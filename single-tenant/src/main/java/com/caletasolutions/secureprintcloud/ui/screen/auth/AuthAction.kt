package com.caletasolutions.secureprintcloud.ui.screen.auth

interface AuthAction {
    data object OnTogglePasswordVisibilityClick : AuthAction
    data object OnLoginClick : AuthAction


}