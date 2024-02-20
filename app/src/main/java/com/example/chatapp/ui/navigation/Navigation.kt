package com.example.chatapp.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.ui.view.LoginScreen
import com.example.chatapp.ui.view.RegisterScreen
import com.example.chatapp.ui.view.home.ChatsScreen
import com.example.chatapp.ui.view.home.ContactsScreen
import com.example.chatapp.ui.view.home.HomeScreen
import com.example.chatapp.ui.view.home.SettingsScreen
import com.example.chatapp.ui.viewmodel.AuthenticationViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = Login.route) {
        composable(
            Login.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -1000 },
                    animationSpec = tween(700)
                ) + fadeIn(animationSpec = tween(700))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(700)
                ) + fadeOut(animationSpec = tween(700))
            }
        ) {
            LoginScreen(
                navController = navController,
                authenticationViewModel = authenticationViewModel
            )
        }
        composable(
            Register.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(700)) + fadeIn(
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 1000 },
                    animationSpec = tween(700)
                ) + fadeOut(animationSpec = tween(700))
            }
        ) {
            RegisterScreen(
                navController = navController,
                authenticationViewModel = authenticationViewModel
            )
        }
        composable(
            Home.route,
        ) {
            HomeScreen(
                authenticationViewModel = authenticationViewModel
            )
        }
    }

}

@Composable
fun HomeNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Contacts.route) {
        composable(Contacts.route) { ContactsScreen() }
        composable(Chats.route) { ChatsScreen() }
        composable(Settings.route) { SettingsScreen() }
    }
}