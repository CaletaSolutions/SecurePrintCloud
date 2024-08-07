package com.caletasolutions.core.data.logger

import timber.log.Timber

class CaletaDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return "${element.fileName}:${element.lineNumber}:${element.methodName}()"
    }
}