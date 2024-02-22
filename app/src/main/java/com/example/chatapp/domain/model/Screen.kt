package com.example.chatapp.domain.model

enum class Screen(val route: String) {
    Contacts("contacts"),
    Chats("chats"),
    Settings("settings");

    companion object {
        fun fromRoute(route: String?): Screen? = Screen.values().find { it.route == route }
    }
}