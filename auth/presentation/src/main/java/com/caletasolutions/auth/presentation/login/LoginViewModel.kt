@file:OptIn(ExperimentalFoundationApi::class)

package com.caletasolutions.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caletasolutions.auth.domain.LoginService
import kotlinx.coroutines.launch

class LoginViewModel(private val loginService: LoginService) : ViewModel() {
    var state: LoginState by mutableStateOf(LoginState())
        private set


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