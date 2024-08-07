package com.caletasolutions.auth.domain

interface LoginService {
    suspend fun verifyCardData(cardNumber: String?)

    suspend fun onLoginClick()
}