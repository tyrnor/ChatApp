@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.chatapp.ui.view

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.chatapp.R
import com.example.chatapp.common.rememberImeState
import com.example.chatapp.domain.model.LoginState
import com.example.chatapp.ui.composable.TopBar
import com.example.chatapp.ui.navigation.Home
import com.example.chatapp.ui.navigation.Register
import com.example.chatapp.ui.theme.AppTheme.colorScheme
import com.example.chatapp.ui.theme.AppTheme.shape
import com.example.chatapp.ui.theme.AppTheme.size
import com.example.chatapp.ui.theme.AppTheme.typography
import com.example.chatapp.ui.theme.Black
import com.example.chatapp.ui.theme.Grey
import com.example.chatapp.ui.theme.LightGrey
import com.example.chatapp.ui.theme.White
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
        Body(authenticationViewModel, loginFailed, loginErrorMessage)
        Footer(navController)
    }
}

@Composable
fun Body(
    authenticationViewModel: AuthenticationViewModel,
    loginFailed: Boolean,
    loginErrorMessage: String,
) {

    var email by rememberSaveable { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }


    Column {
        ImageLogo(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.size(size.medium))
        Email(email = email, emailError = emailError) {
            email = it
        }
        Spacer(modifier = Modifier.size(size.small))
        Password(password = password, passwordError = passwordError) {
            password = it
        }
        Spacer(modifier = Modifier.size(size.small))
        ForgotPassword(modifier = Modifier.align(Alignment.End))
        Spacer(modifier = Modifier.size(size.medium))
        if (loginFailed) LoginFailedMessage(loginErrorMessage)
        LoginButton(
            email = email,
            password = password,
            authenticationViewModel,
            setEmailError = { emailError = it },
            setPasswordError = { passwordError = it }
        ) {
            authenticationViewModel.login(email, password)
        }
        Spacer(modifier = Modifier.size(size.medium))
        LoginDivider()
        Spacer(modifier = Modifier.size(size.medium))
        SocialLogin()
    }
}

@Composable
fun LoginFailedMessage(message: String) {
    Row (modifier = Modifier.padding(horizontal = size.small, vertical = size.medium), verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = Icons.Filled.Error, contentDescription = "error icon", tint = colorScheme.error)
        Text(
            text = message,
            style = typography.labelNormal,
            modifier = Modifier.padding(horizontal = size.small)
        )
    }

}

@Composable
fun ImageLogo(modifier: Modifier) {
    Image(
        painter = painterResource(id = if (!isSystemInDarkTheme()) R.drawable.logo else R.drawable.logo_darkmode),
        contentDescription = "Logo",
        modifier = modifier.background(colorScheme.background)
    )
}


@Composable
fun Email(
    email: String,
    emailError: Boolean,
    onTextChanged: (String) -> Unit,
) {
    TextField(
        value = email,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = size.small)
            .border(
                1.dp,
                color = if (emailError) colorScheme.error else colorScheme.primary,
                shape = shape.container
            ),
        placeholder = { Text(text = "Email", style = typography.body) },
        textStyle = typography.body,
        isError = emailError,
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorScheme.onSecondary,
            containerColor = colorScheme.secondary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        shape = shape.container,
        trailingIcon = {
            if (emailError) {
                Icon(imageVector = Icons.Filled.Error, contentDescription = "error icon")
            }
        }
    )
    if (emailError) {
        Text(
            text = "Email cannot be empty",
            color = colorScheme.error,
            style = typography.labelSmall,
            modifier = Modifier.padding(start = size.small, top = size.small)
        )
    }
}

@Composable
fun Password(
    password: String,
    passwordError: Boolean,
    onTextChanged: (String) -> Unit,
) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    TextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = size.small)
            .border(
                1.dp,
                color = if (passwordError) colorScheme.error else colorScheme.primary,
                shape = shape.container
            ),
        placeholder = { Text(text = "Password", style = typography.body) },
        textStyle = typography.body,
        maxLines = 1,
        isError = passwordError,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorScheme.onSecondary,
            containerColor = colorScheme.secondary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            val image = if (passwordVisibility) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            if (passwordError) {
                Icon(imageVector = Icons.Filled.Error, contentDescription = "error icon")
            } else {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(imageVector = image, contentDescription = "show pass")
                }
            }

        },
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        shape = shape.container
    )
    if (passwordError) {
        Text(
            text = "Password cannot be empty",
            color = colorScheme.error,
            style = typography.labelSmall,
            modifier = Modifier.padding(start = size.small, top = size.small)
        )
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

@Composable
fun LoginButton(
    email: String,
    password: String,
    authenticationViewModel: AuthenticationViewModel,
    setEmailError: (Boolean) -> Unit,
    setPasswordError: (Boolean) -> Unit,
    onClick: () -> Unit,
) {
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

            if (!hasError) {
                onClick()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = size.small),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.primary,
            contentColor = colorScheme.onPrimary,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = size.small,
        ),
        shape = shape.button
    ) {
        Text(text = "Log In")
    }
}


@Composable
fun LoginDivider() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = size.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier
                .background(LightGrey)
                .height(1.dp)
                .weight(1f)

        )
        Text(
            text = "OR",
            modifier = Modifier.padding(horizontal = size.medium),
            style = typography.labelNormal.copy(fontWeight = FontWeight.Bold),
            color = Grey
        )
        Divider(
            modifier = Modifier
                .background(LightGrey)
                .height(1.dp)
                .weight(1f)

        )
    }
}

@Composable
fun SocialLogin() {
    Button(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = size.small),
        colors = ButtonDefaults.buttonColors(
            containerColor = White,
            contentColor = Black,
        ),
        shape = shape.button,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = size.small
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.google),
            contentDescription = "Test",
            modifier = Modifier.size(size.large)
        )
        Spacer(modifier = Modifier.size(size.small))
        Text(text = "Continue with Google")
    }
}

@Composable
fun Footer(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = size.medium)
    ) {

        Spacer(modifier = Modifier.size(size.large))
        SignUp(navController)
        Spacer(modifier = Modifier.size(size.large))
    }
}

@Composable
fun SignUp(navController: NavController) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(text = "Don't have an account?", style = typography.labelNormal, color = Grey)
        Text(
            text = "Sign Up",
            modifier = Modifier
                .padding(horizontal = size.small)
                .clickable {
                    navController.navigate(
                        route = Register.route
                    )
                },
            style = typography.labelNormal.copy(fontWeight = FontWeight.Bold),
            color = colorScheme.primary
        )
    }
}


