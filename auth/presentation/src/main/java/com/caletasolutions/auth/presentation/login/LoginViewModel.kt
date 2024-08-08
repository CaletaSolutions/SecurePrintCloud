@file:OptIn(ExperimentalFoundationApi::class)

package com.caletasolutions.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caletasolutions.auth.domain.LoginService
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(private val loginService: LoginService) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        getCardData()
    }

    private fun getCardData() = viewModelScope.launch {
        val cardData = loginService.getCardData()
        if (cardData != null) {
            //Card login
            Timber.i("Card data found: $cardData")
            state = state.copy(
                isCardLoginFlow = true,
                cardNumber = "$cardData",
                isDecisionPending = false,
            )
        } else {
            //Manual login
            state = state.copy(
                isCardLoginFlow = false,
                isDecisionPending = false,
            )
        }
    }


    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnTogglePasswordVisibilityClick -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }
        }
    }

    fun onLoginClick() = viewModelScope.launch {
        loginService.onLoginClick()
    }

}