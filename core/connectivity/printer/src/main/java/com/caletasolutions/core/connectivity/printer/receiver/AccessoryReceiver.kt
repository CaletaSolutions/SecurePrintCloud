package com.caletasolutions.core.connectivity.printer.receiver


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.caletasolutions.core.connectivity.domain.channels.CardTapNotifier
import com.caletasolutions.core.connectivity.printer.tasks.WorkpathInitializationTask
import com.caletasolutions.core.event.SDKEvent
import com.hp.workpath.api.Result
import com.hp.workpath.api.Workpath
import com.hp.workpath.api.access.AccessService
import com.hp.workpath.api.access.Principal
import com.hp.workpath.api.accessory.hid.AccessoryService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.ref.WeakReference

class AccessoryReceiver : BroadcastReceiver() {

    private var mContextRef: WeakReference<Context?>? = null
    private var appContext: Context? = null
    private var mAccessoryContextId: String? = null
    private var mOrdinal: Long = 0
    private lateinit var sdkInitTask: WorkpathInitializationTask
    private val receiverScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    companion object {
        private const val STATUS_ACTION = "com.hp.workpath.action.ACCESSORY_STATUS_ACTION"
        private const val REPORT_ACTION = "com.hp.workpath.action.ACCESSORY_REPORT_ACTION"
        private var isInitializedSDK = false
        private var isFirstResend = true
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        appContext = context?.applicationContext
        mContextRef = WeakReference(appContext)
        if (isFirstResend) {
            isFirstResend = false
            mContextRef?.get()?.let { initializeWorkpathSdk(it) }
        }
        if (STATUS_ACTION == intent?.action || REPORT_ACTION == intent?.action) {
            val action = intent.action
            val vendorId = intent.getIntExtra("ACCESSORY_INFO_VENDER_ID", -1)
            val productId = intent.getIntExtra("ACCESSORY_INFO_PRODUCT_ID", -1)

            if (vendorId != -1 && productId != -1) {
                when (action) {
                    STATUS_ACTION -> onCardReaderContextChange(intent)
                    REPORT_ACTION -> onCardDataReceive(intent)
                }
            }
        }
    }

    private fun initializeWorkpathSdk(context: Context) {
        //sdkInitTask = WorkpathInitializationTask(context)
        mContextRef?.get()?.let { Workpath.getInstance().initialize(it) }
        isInitializedSDK = true
        /*receiverScope.launch {
            try {
                withContext(Dispatchers.Default) {
                    sdkInitTask.initialize()
                }
                observeSdkInitialization(sdkInitTask)
            } catch (e: Exception) {
                Timber.e("SDK Initialization Failed: ${e.localizedMessage}")
            }
        }*/
    }

    private suspend fun observeSdkInitialization(sdkInitTask: WorkpathInitializationTask) {
        sdkInitTask.sdkInitializationUpdate.collect { event ->
            when (event) {
                is SDKEvent.SDKInitializationError -> {
                    isInitializedSDK = false
                    Timber.e("SDK Initialization Error: ${event.tr.localizedMessage}")
                }

                is SDKEvent.SDKInitializationSuccess -> {
                    isInitializedSDK = true
                    Timber.i("SDK Initialization Success")
                }
            }
        }
    }

    private fun onCardReaderContextChange(intent: Intent) {
        val eventCode = intent.getIntExtra("ACCESSORY_INFO_EVENT_CODE", -1)
        val accessoryContextId = intent.getStringExtra("ACCESSORY_INFO_CONTEXT_ID")

        when (eventCode) {
            0, 1 -> {
                if (!isInitializedSDK) {
                    Timber.d("Initialized SDK is false")
                    return
                }
                if (accessoryContextId != mAccessoryContextId) {
                    mAccessoryContextId = accessoryContextId
                    accessoryContextId?.let { openAccessory(it) }
                }
            }

            2 -> {
                mAccessoryContextId = null
                Timber.e("Accessory context is expired")
            }
        }
    }

    private fun openAccessory(accessoryContextId: String) {
        val result = Result()
        Timber.d("Accessory opened: $accessoryContextId")
        appContext?.let { AccessoryService.open(it, accessoryContextId, result) }
        Timber.i("AccessoryService.open(): $result")
        if (result.code == Result.RESULT_OK) {
            startReadingAccessory(accessoryContextId)
        }
    }

    private fun startReadingAccessory(accessoryContextId: String) {
        val result = Result()
        Timber.d("Accessory startReading: $accessoryContextId")
        appContext?.let { AccessoryService.startReading(it, accessoryContextId, result) }
        Timber.i("AccessoryService.startReading(): $result")
    }

    private fun onCardDataReceive(intent: Intent) {
        Timber.i("AccessoryObserver onReceive()")
        val reportOrdinal = intent.getLongExtra("ACCESSORY_REPORT_INFO_ORDINAL", -1)
        if (mOrdinal != reportOrdinal) {
            mOrdinal = reportOrdinal
            val principal = currentPrincipal
            if (principal != null && !principal.isAuthenticated) {
                val unformattedCardNumber = intent.getStringExtra("ACCESSORY_REPORT_INFO_DATA")
                unformattedCardNumber?.let { initiateSignIn(it) }
            } else {
                signOut()
            }
        } else {
            // Not sure yet, but this could be the place where we receive the same data from the accessory
        }
    }

    private val currentPrincipal: Principal?
        get() {
            val result = Result()
            Timber.d("getCurrentPrincipal")
            val principal = appContext?.let { AccessService.getCurrentPrincipal(it, result) }
            return if (result.code == Result.RESULT_OK) {
                Timber.d("AccessService.getCurrentPrincipal(): $result")
                principal
            } else {
                null
            }
        }

    private fun initiateSignIn(cardNumber: String) {
        val result = Result()
        Timber.d("initiateSignIn")
        appContext?.let { AccessService.initiateSignIn(it, result) }
        Timber.i("AccessService.initiateSignIn(): $result")
        receiverScope.launch {
            CardTapNotifier.channel.send(cardNumber)
            Timber.i("Card data $cardNumber sent to channel")
        }
    }

    private fun signOut() {
        val result = Result()
        Timber.d("SignOut called")
        appContext?.let { AccessService.signOut(it, result) }
        Timber.i("AccessService.signOut(): $result")
    }
}