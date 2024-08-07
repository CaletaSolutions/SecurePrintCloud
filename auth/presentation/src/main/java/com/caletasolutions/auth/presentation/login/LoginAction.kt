package com.caletasolutions.auth.presentation.login

interface LoginAction {
    data object OnTogglePasswordVisibilityClick : LoginAction
    data object OnLoginClick : LoginAction


}