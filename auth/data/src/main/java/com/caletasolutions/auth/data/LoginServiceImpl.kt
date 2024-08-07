package com.caletasolutions.auth.data

import com.caletasolutions.auth.domain.LoginService
import com.caletasolutions.core.domain.printer.WorkpathService

class LoginServiceImpl(private val workpathService: WorkpathService) : LoginService {
    override suspend fun verifyCardData(cardNumber: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun onLoginClick() {
        workpathService.initiateAuthentication()
    }
}