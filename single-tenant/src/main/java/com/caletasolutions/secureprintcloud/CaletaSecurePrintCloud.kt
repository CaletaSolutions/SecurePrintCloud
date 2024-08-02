package com.caletasolutions.secureprintcloud

import android.app.Application
import android.util.Log
import com.caletasolutions.core.event.SDKEvent
import com.caletasolutions.core.tasks.InitializationTask
import com.caletasolutions.secureprintcloud.di.singleTenantModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.context.startKoin

class CaletaSecurePrintCloud : Application() {
    private lateinit var sdkInitTask: InitializationTask
    private val applicationScope = CoroutineScope(Job() + Dispatchers.Main)
    private val tag = "CaletaSecurePrintCloud"

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(singleTenantModule)
        }


        sdkInitTask = InitializationTask(applicationContext)
        applicationScope.launch(Dispatchers.Default) {
            sdkInitTask.initialize()
        }
        applicationScope.launch(Dispatchers.Main) {
            sdkInitTask.sdkInitializationUpdate.collect { event ->
                when(event){
                    is SDKEvent.SDKInitializationError -> {
                        Log.e(tag, "SDK Initialization Error ${event.tr.localizedMessage}")
                    }
                    is SDKEvent.SDKInitializationSuccess -> {
                        Log.i(tag, "SDK Initialization Success")
                    }
                }
            }

        }
    }

    override fun onTerminate() {
        super.onTerminate()
        applicationScope.cancel()
    }
}