@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.chatapp.ui.view

import android.app.Activity
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.example.chatapp.R
import com.example.chatapp.common.rememberImeState
import com.example.chatapp.ui.composable.TopBar
import com.example.chatapp.ui.theme.AppTheme.colorScheme
import com.example.chatapp.ui.theme.AppTheme.shape
import com.example.chatapp.ui.theme.AppTheme.size
import com.example.chatapp.ui.theme.AppTheme.typography
import com.example.chatapp.ui.theme.Black
import com.example.chatapp.ui.theme.Grey
import com.example.chatapp.ui.theme.LightGrey
import com.example.chatapp.ui.theme.Purple
import com.example.chatapp.ui.theme.White

@Composable
fun LoginScreen(navController: NavController) {
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.scrollTo(scrollState.maxValue / 2)
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
        Header()
        Body()
        Footer()
    }
}

@Composable
fun Header() {
    val activity = LocalContext.current as Activity
    TopBar(icon = Icons.Filled.Close, iconDescription = "Close App") {
        activity.finish()
    }
}

@Composable
fun Body() {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isLoginEnable by rememberSaveable { mutableStateOf(false) }
    Column {
        ImageLogo(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.size(size.medium))
        Email(email = email) {
            email = it
            isLoginEnable = enableLogin(email, password)
        }
        Spacer(modifier = Modifier.size(size.small))
        Password(password) {
            password = it
            isLoginEnable = enableLogin(email, password)
        }
        Spacer(modifier = Modifier.size(size.small))
        ForgotPassword(modifier = Modifier.align(Alignment.End))
        Spacer(modifier = Modifier.size(size.medium))
        LoginButton(isLoginEnable)
        Spacer(modifier = Modifier.size(size.medium))
        LoginDivider()
        Spacer(modifier = Modifier.size(size.medium))
        SocialLogin()
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
fun Email(email: String, onTextChanged: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = size.small)
            .border(1.dp, color = colorScheme.primary, shape = shape.container),
        placeholder = { Text(text = "Email", style = typography.body) },
        textStyle = typography.body,
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
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = shape.container,
    )
}

@Composable
fun Password(password: String, onTextChanged: (String) -> Unit) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    TextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = size.small)
            .border(1.dp, color = colorScheme.primary, shape = shape.container),
        placeholder = { Text(text = "Password", style = typography.body) },
        textStyle = typography.body,
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorScheme.onSecondary,
            containerColor = colorScheme.secondary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            val image = if (passwordVisibility) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = image, contentDescription = "show pass")
            }
        },
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        shape = shape.container
    )
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
fun LoginButton(loginEnable: Boolean) {
    Button(
        onClick = { },
        enabled = loginEnable,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = size.small),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.primary,
            disabledContainerColor = Purple,
            contentColor = colorScheme.onPrimary,
            disabledContentColor = colorScheme.onPrimary
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = size.small,
            disabledElevation = size.small
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
fun Footer() {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = size.medium)) {

        Spacer(modifier = Modifier.size(size.large))
        SignUp()
        Spacer(modifier = Modifier.size(size.large))
    }
}

@Composable
fun SignUp() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(text = "Don't have an account?", style = typography.labelNormal, color = Grey)
        Text(
            text = "Sign Up",
            modifier = Modifier.padding(horizontal = size.small),
            style = typography.labelNormal.copy(fontWeight = FontWeight.Bold),
            color = colorScheme.primary
        )
    }
}

fun enableLogin(email: String, password: String): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6

