package com.example.chatapp.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.domain.model.NavigationDirection
import com.example.chatapp.ui.view.LoginScreen
import com.example.chatapp.ui.view.RegisterScreen
import com.example.chatapp.ui.view.home.ChatsScreen
import com.example.chatapp.ui.view.home.ContactsScreen
import com.example.chatapp.ui.view.home.HomeScreen
import com.example.chatapp.ui.view.home.SearchScreen
import com.example.chatapp.ui.view.home.SettingsScreen
import com.example.chatapp.ui.viewmodel.AuthenticationViewModel
import com.example.chatapp.ui.viewmodel.HomeViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = Login.route) {
        composable(Login.route, enterTransition = {
            slideInHorizontally(
                initialOffsetX = { -1000 }, animationSpec = tween(700)
            ) + fadeIn(animationSpec = tween(700))
        }, exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -1000 }, animationSpec = tween(700)
            ) + fadeOut(animationSpec = tween(700))
        }) {
            LoginScreen(
                navController = navController, authenticationViewModel = authenticationViewModel
            )
        }
        composable(Register.route, enterTransition = {
            slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(700)) + fadeIn(
                animationSpec = tween(700)
            )
        }, exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { 1000 }, animationSpec = tween(700)
            ) + fadeOut(animationSpec = tween(700))
        }) {
            RegisterScreen(
                navController = navController, authenticationViewModel = authenticationViewModel
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
fun HomeNavigation(navController: NavHostController, homeViewModel: HomeViewModel) {
    NavHost(navController = navController, startDestination = Contacts.route) {
        composable(Contacts.route, enterTransition = {
            if (homeViewModel.navigationDirection.value == NavigationDirection.RIGHT_TO_LEFT) {
                slideInHorizontally(
                    initialOffsetX = { -1000 }, animationSpec = tween(700)
                ) + fadeIn(
                    animationSpec = tween(700)
                )
            } else {
                fadeIn(animationSpec = tween(100))
            }
        }, exitTransition = {
            if (homeViewModel.navigationDirection.value == NavigationDirection.LEFT_TO_RIGHT) {
                slideOutHorizontally(
                    targetOffsetX = { -1000 }, animationSpec = tween(700)
                ) + fadeOut(animationSpec = tween(700))
            } else {
                slideOutHorizontally(
                    targetOffsetX = { -1000 }, animationSpec = tween(100)
                ) + fadeOut(animationSpec = tween(100))
            }

        }) { ContactsScreen(navController) }
        composable(Chats.route, enterTransition = {
            if (homeViewModel.navigationDirection.value == NavigationDirection.LEFT_TO_RIGHT) {
                slideInHorizontally(
                    initialOffsetX = { 1000 }, animationSpec = tween(700)
                ) + fadeIn(
                    animationSpec = tween(700)
                )
            } else {
                slideInHorizontally(
                    initialOffsetX = { -1000 }, animationSpec = tween(700)
                ) + fadeIn(animationSpec = tween(700))
            }
        }, exitTransition = {
            if (homeViewModel.navigationDirection.value == NavigationDirection.LEFT_TO_RIGHT) {
                slideOutHorizontally(
                    targetOffsetX = { -1000 }, animationSpec = tween(700)
                ) + fadeOut(animationSpec = tween(700))
            } else {
                slideOutHorizontally(
                    targetOffsetX = { 1000 }, animationSpec = tween(700)
                ) + fadeOut(animationSpec = tween(700))
            }
        }) { ChatsScreen() }
        composable(Settings.route, enterTransition = {
            slideInHorizontally(
                initialOffsetX = { 1000 }, animationSpec = tween(700)
            ) + fadeIn(
                animationSpec = tween(700)
            )
        }, exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { 1000 }, animationSpec = tween(700)
            ) + fadeOut(animationSpec = tween(700))

        }) { SettingsScreen() }
        composable(Search.route,
            enterTransition = {
                fadeIn(animationSpec = tween(100))
            },
            exitTransition = { fadeOut(animationSpec = tween(100)) })
        {
            SearchScreen(navController)
            homeViewModel.resetNavigationDirection()
        }
    }
}

@Composable
fun NavController.isCurrentRoute(route: String): Boolean {
    val navBackStackEntry by currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    return currentRoute?.startsWith(route) == true
}
