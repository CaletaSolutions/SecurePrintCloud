package com.caletasolutions.auth.presentation.login
sealed interface LoginEvent {

    data class OnCardUidReceived(val cardNumber: String): LoginEvent
    data class Error(val error: String): LoginEvent
}