@file:OptIn(
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class
)

package com.caletasolutions.secureprintcloud.ui.screen.auth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.caletasolutions.secureprintcloud.R
import com.caletasolutions.secureprintcloud.ui.component.GradientBackground
import com.caletasolutions.secureprintcloud.ui.component.SecurePrintActionButton
import com.caletasolutions.secureprintcloud.ui.component.SecurePrintPasswordTextField
import com.caletasolutions.secureprintcloud.ui.component.SecurePrintScaffold
import com.caletasolutions.secureprintcloud.ui.component.SecurePrintTextField
import com.caletasolutions.secureprintcloud.ui.theme.CheckIcon
import com.caletasolutions.secureprintcloud.ui.theme.EmailIcon
import com.caletasolutions.secureprintcloud.ui.theme.SecurePrintTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthScreenRoot(
    onLogInClick: () -> Unit,
    onSuccessfulAuthentication: () -> Unit,
    viewModel: AuthViewModel = koinViewModel()

) {

    AuthScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is AuthAction.OnTogglePasswordVisibilityClick -> {
                    viewModel.onAction(action)
                }

                is AuthAction.OnLoginClick -> {
                    onLogInClick()
                }
            }
        }
    )

}

@Composable
fun AuthScreen(state: AuthState, onAction: (AuthAction) -> Unit) {
    GradientBackground {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .requiredWidth(500.dp)
                .fillMaxHeight()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp)
                .padding(top = 16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.launcher_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .requiredSize(125.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.sign_in),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            SecurePrintTextField(
                state = state.email,
                startIcon = EmailIcon,
                endIcon = if (state.isEmailValid) CheckIcon else null,
                hint = stringResource(id = R.string.example_email),
                title = stringResource(id = R.string.email),
                modifier = Modifier.fillMaxWidth(),
                additionalInfo = stringResource(id = R.string.must_be_valid_email),
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(16.dp))
            SecurePrintPasswordTextField(
                state = state.password,
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(AuthAction.OnTogglePasswordVisibilityClick)
                },
                hint = stringResource(id = R.string.password),
                title = stringResource(id = R.string.password),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))

            SecurePrintActionButton(text = stringResource(id = R.string.login),
                isLoading = state.isRegistering,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(AuthAction.OnLoginClick)
                })
        }
    }
}

@Preview(showBackground = true, device = Devices.TABLET)
@Composable
private fun RegisterScreenPreview() {
    SecurePrintTheme {
        SecurePrintScaffold(
            modifier = Modifier.fillMaxSize(),
            withGradient = false
        ) {
            AuthScreen(AuthState(), {})
        }
    }
}