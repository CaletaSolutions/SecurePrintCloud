package com.caletasolutions.secureprintcloud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.caletasolutions.secureprintcloud.ui.component.SecurePrintScaffold
import com.caletasolutions.secureprintcloud.ui.screen.auth.AuthScreenRoot
import com.caletasolutions.secureprintcloud.ui.theme.SecurePrintTheme
import com.hp.workpath.api.Result
import com.hp.workpath.api.access.AccessService
import com.hp.workpath.api.access.AuthenticationAttributes
import com.hp.workpath.api.access.AuthenticationAttributes.WindowsBuilder
import com.hp.workpath.api.access.SignInAction

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SecurePrintTheme {
                SecurePrintScaffold(
                    modifier = Modifier.fillMaxSize(),
                    withGradient = false
                ) { innerPadding ->
                    AuthScreenRoot(
                        onLogInClick = {
                            val result = Result()
                            val signInAction = SignInAction(SignInAction.Action.SUCCESS, null)
                            AccessService.signIn(
                                this@MainActivity,
                                signInAction,
                                windowsData,
                                result
                            )
                        },
                        onSuccessfulAuthentication = { })
                }
            }
        }
    }

    @get:Throws(Exception::class)
    private val windowsData: AuthenticationAttributes
        get() {
            return WindowsBuilder()
                .setFullyQualifiedName("//dkdonohoe//HGUPTA")
                .setDisplayName("hgupta")
                .setPassword("password")
                .setUserDomain("caleta.com")
                .setUserEmail("hgupta@caleta.com")
                .setUserName("hgupta")
                .setUserPrincipalName("Hritik Gupta")
                .setUserOverridesAttributes(null)
                .setUserPreferencesAttributes(null)
                .build()
        }
}


