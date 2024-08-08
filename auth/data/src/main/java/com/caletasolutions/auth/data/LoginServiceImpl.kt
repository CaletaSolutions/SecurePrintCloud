package com.caletasolutions.auth.data

import com.caletasolutions.auth.domain.LoginService
import com.caletasolutions.auth.domain.formatCardNumber
import com.caletasolutions.core.domain.printer.WorkpathService

class LoginServiceImpl(private val workpathService: WorkpathService) : LoginService {
    override suspend fun verifyCardData(cardNumber: String?) {

    }

    override suspend fun getCardData() : String ? {
        return workpathService.getCardData().let { it?.formatCardNumber() }
    }

    override suspend fun onLoginClick() {
        workpathService.initiateAuthentication()
    }
}