@file:OptIn(ExperimentalFoundationApi::class)

package com.caletasolutions.secureprintcloud.ui.screen.auth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState

data class AuthState(
    val email : TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val password: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val isRegistering: Boolean = false,
    val canRegister : Boolean = false
)
