package com.caletasolutions.core.data.printer

import android.content.Context
import com.caletasolutions.core.connectivity.printer.tasks.AuthenticationTask
import com.caletasolutions.core.domain.printer.WorkpathService

class WorkpathServiceImpl(context: Context) : WorkpathService {

    private var authenticationTask: AuthenticationTask = AuthenticationTask(context)

    override suspend fun initiateAuthentication() {
        authenticationTask.initiateAuthentication()
    }

}