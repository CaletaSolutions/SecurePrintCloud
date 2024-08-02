package com.caletasolutions.core.tasks

import android.content.Context
import android.util.Log
import com.caletasolutions.core.enum.InitializationStatus
import com.caletasolutions.core.event.SDKEvent
import com.hp.workpath.api.SsdkUnsupportedException
import com.hp.workpath.api.Workpath
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext

class InitializationTask(private var context : Context) {

    private val tag = "InitializationTask"
    private val sdkEventChannel = Channel<SDKEvent>()
    val sdkInitializationUpdate = sdkEventChannel.receiveAsFlow()

    suspend fun initialize() {
        var status = InitializationStatus.NO_ERROR
        try {
            Log.i(tag, "Initializing Workpath SDK")
            Workpath.getInstance().initialize(context)
        } catch (sue: SsdkUnsupportedException) {
            status = InitializationStatus.INIT_EXCEPTION
            Log.e(tag, "SsdkUnsupportedException = ${sue.cause} - ${sue.message} - ${sue.localizedMessage}")
            sdkEventChannel.send(SDKEvent.SDKInitializationError(sue))
        } catch (se: SecurityException) {
            status = InitializationStatus.INIT_EXCEPTION
            Log.e(tag, "SecurityException = ${se.cause} - ${se.message} - ${se.localizedMessage}")
            sdkEventChannel.send(SDKEvent.SDKInitializationError(se))
        } catch (t: Throwable) {
            status = InitializationStatus.INIT_EXCEPTION
            Log.e(tag, "Throwable - Workpath Initialization Failed = ${t.cause} - ${t.message} - ${t.localizedMessage}")
            sdkEventChannel.send(SDKEvent.SDKInitializationError(t))
        } catch (e : Exception){
            status = InitializationStatus.OTHER
            Log.e(tag, "Exception - Workpath Initialization Failed = ${e.cause} ${e.message} ${e.localizedMessage}")
            sdkEventChannel.send(SDKEvent.SDKInitializationError(e))
        }
        onPostExecute(status)
    }
    private suspend fun onPostExecute(status: InitializationStatus) {
        withContext(Dispatchers.Main) {
            if (status == InitializationStatus.NO_ERROR) {
                Log.i(tag, "Initialization Completed")
                sdkEventChannel.send(SDKEvent.SDKInitializationSuccess("Initialization Completed"))
            } else {
                when (status) {
                    InitializationStatus.INIT_EXCEPTION -> {
                        Log.e(tag, "Initialization Error")
                    }
                    InitializationStatus.NOT_SUPPORTED -> {
                        Log.e(tag, "Not Supported Error")
                    }
                    InitializationStatus.OTHER -> {
                        Log.e(tag, "Other Error")
                    }
                    else -> {
                        Log.e(tag, "God Knows, What happened !")
                    }
                }
            }
        }
    }
}