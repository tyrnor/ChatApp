package com.example.chatapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.ui.view.HomeScreen
import com.example.chatapp.ui.view.LoginScreen
import com.example.chatapp.ui.view.RegisterScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Login.route) {
        composable(Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(Home.route){
            HomeScreen(navController = navController)
        }
    }
}