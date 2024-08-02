package com.caletasolutions.core.util

sealed interface WorkpathError : Error {
    enum class Initialization : WorkpathError {
        NO_INTERNET,
        NO_LOCATION,
        NO_PERMISSION,
        NO_STORAGE,
        UNKNOWN
    }
}