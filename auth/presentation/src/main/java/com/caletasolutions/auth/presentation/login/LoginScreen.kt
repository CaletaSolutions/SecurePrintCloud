@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class
)

package com.caletasolutions.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.caletasolutions.auth.presentation.R
import com.caletasolutions.core.presentation.designsystem.CardTapIcon
import com.caletasolutions.core.presentation.designsystem.CheckIcon
import com.caletasolutions.core.presentation.designsystem.EmailIcon
import com.caletasolutions.core.presentation.designsystem.SecurePrintTheme
import com.caletasolutions.core.presentation.designsystem.components.LoginFormHeader
import com.caletasolutions.core.presentation.designsystem.components.SecurePrintActionButton
import com.caletasolutions.core.presentation.designsystem.components.SecurePrintLabel
import com.caletasolutions.core.presentation.designsystem.components.SecurePrintPasswordTextField
import com.caletasolutions.core.presentation.designsystem.components.SecurePrintScaffold
import com.caletasolutions.core.presentation.designsystem.components.SecurePrintTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel()
) {
    AuthScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is LoginAction.OnTogglePasswordVisibilityClick -> {
                    viewModel.onAction(action)
                }

                is LoginAction.OnLoginClick -> {
                    viewModel.onLoginClick()
                }
            }
        }
    )

}

@Composable
fun AuthScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    if (state.isDecisionPending) {
        LoginFormHeader {
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

    } else {
        if (state.isCardLoginFlow) {
            CardLoginLayout(cardNumber = state.cardNumber)
        } else {
            ManualLoginLayout(state = state, onAction = onAction)
        }
    }
}

@Composable
fun CardLoginLayout(cardNumber: String?) {
    LoginFormHeader {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.card_sign_in),
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        SecurePrintLabel(
            state = TextFieldState("Received Card UID $cardNumber"),
            startIcon = CardTapIcon,
            endIcon = CheckIcon,
            title = stringResource(id = R.string.card),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ManualLoginLayout(state: LoginState, onAction: (LoginAction) -> Unit) {
    LoginFormHeader {
        Text(
            text = stringResource(id = R.string.sign_in),
            style = MaterialTheme.typography.headlineLarge
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
                onAction(LoginAction.OnTogglePasswordVisibilityClick)
            },
            hint = stringResource(id = R.string.password),
            title = stringResource(id = R.string.password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

        SecurePrintActionButton(text = stringResource(
            id = R.string.submit
        ),
            isLoading = state.isRegistering,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onAction(LoginAction.OnLoginClick)
            })
    }
}

//@Preview(showBackground = true, device = Devices.TABLET)
@Composable
private fun ManualLoginFlowScreenPreview() {
    SecurePrintTheme {
        SecurePrintScaffold(
            modifier = Modifier.fillMaxSize(),
        ) {
            ManualLoginLayout(LoginState(), {})
        }
    }
}

@Preview(showBackground = true, device = Devices.TABLET)
@Composable
private fun CardLoginFlowScreenPreview() {
    SecurePrintTheme {
        SecurePrintScaffold(
            modifier = Modifier.fillMaxSize(),
        ) {
            CardLoginLayout(cardNumber = "E469YH90")
        }
    }
}