package com.example.chatapp.ui.view

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
import androidx.compose.material.icons.filled.ArrowBackIos
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import com.example.chatapp.ui.composables.textfields.UsernameTextField
import com.example.chatapp.ui.navigation.Home
import com.example.chatapp.ui.navigation.Login
import com.example.chatapp.ui.theme.AppTheme.colorScheme
import com.example.chatapp.ui.theme.AppTheme.size
import com.example.chatapp.ui.theme.AppTheme.typography
import com.example.chatapp.ui.theme.DarkGrey
import com.example.chatapp.ui.viewmodel.AuthenticationViewModel

@Composable
fun RegisterScreen(navController: NavController, authenticationViewModel: AuthenticationViewModel) {
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    val loginState by authenticationViewModel.loginState.collectAsState()
    val loginFailed by authenticationViewModel.loginFailed.collectAsState()

    var loginErrorMessage by remember {
        mutableStateOf("")
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
        TopBar(icon = Icons.Filled.ArrowBackIos, iconDescription = "Back") {
            navController.popBackStack()
        }
        BodyRegister(
            authenticationViewModel = authenticationViewModel,
            loginFailed,
            loginErrorMessage
        )
        Footer(text1 = "Already have an account?", text2 = "Sign In") {
            navController.navigate(Login.route)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BodyRegister(
    authenticationViewModel: AuthenticationViewModel,
    loginFailed: Boolean,
    loginErrorMessage: String,
) {

    var email by rememberSaveable { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var username by rememberSaveable { mutableStateOf("") }
    var usernameError by remember { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }

    val controller = LocalSoftwareKeyboardController.current

    Column {
        ImageLogo(modifier = Modifier.align(Alignment.CenterHorizontally))
        Text(
            text = "Register to talk with your friends.",
            style = typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = DarkGrey
        )
        Spacer(modifier = Modifier.size(size.large))
        GoogleSignInButton()
        Spacer(modifier = Modifier.size(size.medium))
        LoginDivider()
        Spacer(modifier = Modifier.size(size.medium))
        EmailTextField(email = email, emailError = emailError) {
            email = it
        }
        Spacer(modifier = Modifier.size(size.small))
        UsernameTextField(username = username, usernameError = usernameError) {
            username = it
        }
        Spacer(modifier = Modifier.size(size.small))
        PasswordTextField(password = password, passwordError = passwordError) {
            password = it
        }
        Spacer(modifier = Modifier.size(size.medium))
        if (loginFailed) LoginFailedMessage(loginErrorMessage)
        LoginRegisterButton(
            email = email,
            username = username,
            password = password,
            text = "Sign Up",
            authenticationViewModel = authenticationViewModel,
            setEmailError = { emailError = it },
            setUsernameError = { usernameError = it },
            setPasswordError = { passwordError = it }
        ) {
            if (!emailError && !usernameError && !passwordError) {
                controller?.hide()
                authenticationViewModel.register(email, password, username)
            }
        }
    }

}

