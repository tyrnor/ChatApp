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
object Contacts : Destinations {
    override val route: String = "home/contacts"
}
object Search : Destinations {
    override val route: String = "home/search"
}
object Chats : Destinations {
    override val route: String = "home/chats"
}
object Settings : Destinations {
    override val route: String = "home/settings"
}