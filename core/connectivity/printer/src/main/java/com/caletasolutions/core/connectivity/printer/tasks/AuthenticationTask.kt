package com.caletasolutions.core.connectivity.printer.tasks

import android.content.Context
import com.hp.workpath.api.Result
import com.hp.workpath.api.access.AccessService
import com.hp.workpath.api.access.AuthenticationAttributes
import com.hp.workpath.api.access.SignInAction

class AuthenticationTask(private var context: Context) {

    @get:Throws(Exception::class)
    private val windowsData: AuthenticationAttributes
        get() {
            return AuthenticationAttributes.WindowsBuilder()
                .setFullyQualifiedName("//dkdonohoe//HGUPTA").setDisplayName("hgupta")
                .setPassword("password").setUserDomain("caleta.com")
                .setUserEmail("hgupta@caleta.com").setUserName("hgupta")
                .setUserPrincipalName("Hritik Gupta").setUserOverridesAttributes(null)
                .setUserPreferencesAttributes(null).build()
        }

    fun initiateAuthentication() {
        val result = Result()
        val signInAction = SignInAction(SignInAction.Action.SUCCESS, null)
        AccessService.signIn(
            context, signInAction, windowsData, result
        )
    }
}