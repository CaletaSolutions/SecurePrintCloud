package com.caletasolutions.core.connectivity.printer.tasks

import android.content.Context
import com.caletasolutions.core.enum.InitializationStatus
import com.caletasolutions.core.event.SDKEvent
import com.hp.workpath.api.SsdkUnsupportedException
import com.hp.workpath.api.Workpath
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext
import timber.log.Timber

class WorkpathInitializationTask(private var context: Context) {
    private val sdkEventChannel = Channel<SDKEvent>()
    val sdkInitializationUpdate = sdkEventChannel.receiveAsFlow()

    suspend fun initialize() {
        var status = InitializationStatus.NO_ERROR
        try {
            Timber.i("Initializing Workpath SDK")
            Workpath.getInstance().initialize(context)
        } catch (sue: SsdkUnsupportedException) {
            status = InitializationStatus.INIT_EXCEPTION
            Timber.e("SdkUnsupportedException = ${sue.cause} - ${sue.message} - ${sue.localizedMessage}")
            sdkEventChannel.send(SDKEvent.SDKInitializationError(sue))
        } catch (se: SecurityException) {
            status = InitializationStatus.INIT_EXCEPTION
            Timber.e("SecurityException = ${se.cause} - ${se.message} - ${se.localizedMessage}")
            sdkEventChannel.send(SDKEvent.SDKInitializationError(se))
        } catch (t: Throwable) {
            status = InitializationStatus.INIT_EXCEPTION
            Timber.e("Throwable - Workpath Initialization Failed = ${t.cause} - ${t.message} - ${t.localizedMessage}")
            sdkEventChannel.send(SDKEvent.SDKInitializationError(t))
        } catch (e: Exception) {
            status = InitializationStatus.OTHER
            Timber.e("Exception - Workpath Initialization Failed = ${e.cause} ${e.message} ${e.localizedMessage}")
            sdkEventChannel.send(SDKEvent.SDKInitializationError(e))
        }
        onPostExecute(status)
    }
    private suspend fun onPostExecute(status: InitializationStatus) {
        withContext(Dispatchers.Main) {
            if (status == InitializationStatus.NO_ERROR) {
                Timber.i("SDK Initialization Completed")
                sdkEventChannel.send(SDKEvent.SDKInitializationSuccess("Initialization Completed"))
            } else {
                when (status) {
                    InitializationStatus.INIT_EXCEPTION -> {
                        Timber.e("Initialization Error")
                    }
                    InitializationStatus.NOT_SUPPORTED -> {
                        Timber.e("Not Supported Error")
                    }
                    InitializationStatus.OTHER -> {
                        Timber.e("Other Error")
                    }
                    else -> {
                        Timber.e("God Knows, What happened !")
                    }
                }
            }
        }
    }


}