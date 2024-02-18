package com.example.chatapp.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.chatapp.data.model.AuthenticatedUser
import com.example.chatapp.domain.model.LoginState
import com.example.chatapp.ui.viewmodel.AuthenticationViewModel
import kotlin.math.log

@Composable
fun HomeScreen(navController: NavController, authenticationViewModel: AuthenticationViewModel) {

    val user by authenticationViewModel.user.collectAsState()
    Column {
        Text(text = "Home Screen")
        user?.let {
            Text(text = "Welcome ${it.displayName ?: "User"}")
        }

    }
}