

package com.example.chatapp.ui.view

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.chatapp.common.rememberImeState
import com.example.chatapp.domain.model.LoginState
import com.example.chatapp.ui.composables.Footer
import com.example.chatapp.ui.composables.ImageLogo
import com.example.chatapp.ui.composables.LoginFailedMessage
import com.example.chatapp.ui.composables.TopBar
import com.example.chatapp.ui.composables.buttons.GoogleSignInButton
import com.example.chatapp.ui.composables.buttons.LoginRegisterButton
import com.example.chatapp.ui.composables.dividers.LoginDivider
import com.example.chatapp.ui.composables.textfields.EmailTextField
import com.example.chatapp.ui.composables.textfields.PasswordTextField
import com.example.chatapp.ui.navigation.Home
import com.example.chatapp.ui.navigation.Register
import com.example.chatapp.ui.theme.AppTheme.colorScheme
import com.example.chatapp.ui.theme.AppTheme.size
import com.example.chatapp.ui.theme.AppTheme.typography
import com.example.chatapp.ui.viewmodel.AuthenticationViewModel


@Composable
fun LoginScreen(navController: NavController, authenticationViewModel: AuthenticationViewModel) {
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val loginState by authenticationViewModel.loginState.collectAsState()
    val loginFailed by authenticationViewModel.loginFailed.collectAsState()

    var loginErrorMessage by remember {
        mutableStateOf("")
    }
    val activity = LocalContext.current as Activity

    
    LaunchedEffect(key1 = true){
        authenticationViewModel.login("test@test.com", "test123")
    }

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.scrollTo(scrollState.maxValue / 2)
        }
    }

    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                navController.navigate(Home.route)
            }

            is LoginState.Error -> {
                loginErrorMessage = (loginState as LoginState.Error).message
                authenticationViewModel.loginFailed()
            }

            else -> {
            }
        }
    }

    Column(
        modifier = Modifier
            .background(colorScheme.background)
            .fillMaxSize()
            .padding(size.normal)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopBar(icon = Icons.Filled.Close, iconDescription = "Close App") {
            activity.finish()
        }
        BodyLogin(
            authenticationViewModel = authenticationViewModel,
            loginFailed = loginFailed,
            loginErrorMessage = loginErrorMessage
        )
        Footer(text1 = "Don't have an account?", text2 = "Sign Up") {
            navController.navigate(Register.route)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BodyLogin(
    authenticationViewModel: AuthenticationViewModel,
    loginFailed: Boolean,
    loginErrorMessage: String,
) {

    var email by rememberSaveable { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }

    val controller = LocalSoftwareKeyboardController.current


    Column {
        ImageLogo(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.size(size.medium))
        EmailTextField(email = email, emailError = emailError) {
            email = it
        }
        Spacer(modifier = Modifier.size(size.small))
        PasswordTextField(password = password, passwordError = passwordError) {
            password = it
        }
        Spacer(modifier = Modifier.size(size.small))
        ForgotPassword(modifier = Modifier.align(Alignment.End))
        Spacer(modifier = Modifier.size(size.medium))
        if (loginFailed) LoginFailedMessage(loginErrorMessage)
        LoginRegisterButton(
            email = email,
            username = null,
            password = password,
            text = "Sign In",
            authenticationViewModel = authenticationViewModel,
            setEmailError = { emailError = it },
            setUsernameError = {},
            setPasswordError = { passwordError = it }
        ) {
            controller?.hide()
            authenticationViewModel.login(email, password)
        }
        Spacer(modifier = Modifier.size(size.medium))
        LoginDivider()
        Spacer(modifier = Modifier.size(size.medium))
        GoogleSignInButton()
    }
}


@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = "Forgot password?",
        style = typography.labelNormal.copy(fontWeight = FontWeight.Bold),
        color = colorScheme.primary,
        modifier = modifier.padding(end = size.small)
    )

}











