@file:OptIn(ExperimentalFoundationApi::class)

package com.caletasolutions.secureprintcloud.ui.screen.auth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    var state by mutableStateOf(AuthState())
        private set


    fun onAction(action: AuthAction) {
        when (action) {
            AuthAction.OnTogglePasswordVisibilityClick -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }
        }

    }

}