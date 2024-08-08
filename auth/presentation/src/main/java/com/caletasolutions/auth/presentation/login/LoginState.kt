@file:OptIn(ExperimentalFoundationApi::class)

package com.caletasolutions.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState

data class LoginState(
    val email : TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val password: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val isRegistering: Boolean = false,
    val canRegister : Boolean = false,

    val isDecisionPending : Boolean = true,
    val isCardLoginFlow : Boolean = false,
    val cardNumber : String? = null,
)
