package com.example.chatapp.ui.navigation

interface Destinations {
    val route: String
}

object Login: Destinations {
    override val route = "login"
}

object Register: Destinations {
    override val route = "register"
}
object Home: Destinations {
    override val route: String = "home"
}