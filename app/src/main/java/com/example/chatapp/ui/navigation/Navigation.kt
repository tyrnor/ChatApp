package com.example.chatapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.ui.view.HomeScreen
import com.example.chatapp.ui.view.LoginScreen
import com.example.chatapp.ui.view.RegisterScreen
import com.example.chatapp.ui.viewmodel.AuthenticationViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val authenticationViewModel : AuthenticationViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = Login.route) {
        composable(Login.route) {
            LoginScreen(navController = navController, authenticationViewModel = authenticationViewModel)
        }
        composable(Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(Home.route){
            HomeScreen(navController = navController, authenticationViewModel = authenticationViewModel)
        }
    }
}