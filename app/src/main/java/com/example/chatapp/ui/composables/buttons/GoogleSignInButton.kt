package com.example.chatapp.ui.composables.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chatapp.R
import com.example.chatapp.ui.theme.AppTheme
import com.example.chatapp.ui.theme.Black
import com.example.chatapp.ui.theme.White
import com.example.chatapp.ui.viewmodel.AuthenticationViewModel

@Composable
fun GoogleSignInButton() {
    val authenticationViewModel : AuthenticationViewModel = hiltViewModel()
    Button(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.size.small),
        colors = ButtonDefaults.buttonColors(
            containerColor = White,
            contentColor = Black,
        ),
        shape = AppTheme.shape.button,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = AppTheme.size.small
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.google),
            contentDescription = "Test",
            modifier = Modifier.size(AppTheme.size.large)
        )
        Spacer(modifier = Modifier.size(AppTheme.size.small))
        Text(text = "Continue with Google")
    }
}
