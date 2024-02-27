package com.example.chatapp.ui.composables.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatapp.domain.model.LoginState
import com.example.chatapp.ui.theme.AppTheme
import com.example.chatapp.ui.viewmodel.AuthenticationViewModel

@Composable
fun LoginRegisterButton(
    email: String,
    username : String?,
    password: String,
    authenticationViewModel: AuthenticationViewModel,
    text : String,
    setEmailError: (Boolean) -> Unit,
    setUsernameError: (Boolean) -> Unit,
    setPasswordError: (Boolean) -> Unit,
    onClick: () -> Unit,
) {
    val loginState by authenticationViewModel.loginState.collectAsState()
    Button(
        onClick = {
            var hasError = false
            if (email.isBlank()) {
                setEmailError(true)
                authenticationViewModel.resetLoginFailed()
                hasError = true
            } else {
                setEmailError(false)
            }

            if (password.isBlank()) {
                setPasswordError(true)
                authenticationViewModel.resetLoginFailed()
                hasError = true
            } else {
                setPasswordError(false)
            }

            if (username != null) {
                if (username.isBlank()){
                    setUsernameError(true)
                    authenticationViewModel.resetLoginFailed()
                    hasError = true
                } else {
                    setUsernameError(false)
                }
            }

            if (!hasError) {
                onClick()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.size.small),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colorScheme.primary,
            contentColor = AppTheme.colorScheme.onPrimary,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = AppTheme.size.small,
        ),
        shape = AppTheme.shape.button
    ) {
        if (loginState is LoginState.Loading) {
            CircularProgressIndicator(
                color = AppTheme.colorScheme.onPrimary,
                strokeWidth = 2.dp,
                modifier = Modifier.size(AppTheme.size.medium)
            )
        } else {
            Text(text = text, style = AppTheme.typography.labelLarge)
        }
    }
}