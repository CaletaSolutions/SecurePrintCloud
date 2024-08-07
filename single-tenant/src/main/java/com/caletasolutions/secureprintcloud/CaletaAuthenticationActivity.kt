package com.caletasolutions.secureprintcloud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.caletasolutions.auth.presentation.login.LoginScreenRoot
import com.caletasolutions.core.presentation.designsystem.SecurePrintTheme
import com.caletasolutions.core.presentation.designsystem.components.SecurePrintScaffold


class CaletaAuthenticationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SecurePrintTheme {
                SecurePrintScaffold(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    LoginScreenRoot()
                }
            }
        }
    }

}



