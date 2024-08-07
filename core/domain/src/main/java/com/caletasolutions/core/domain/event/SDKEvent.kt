package com.caletasolutions.core.event

sealed class SDKEvent{
    data class SDKInitializationError(val tr :Throwable) : SDKEvent()
    data class SDKInitializationSuccess(val msg :String) : SDKEvent()
}
