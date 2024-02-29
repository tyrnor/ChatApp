package com.example.chatapp.common

object FirestoreUtils {
    val SEARCH_NAME_PATTERN = "[ _.,?!@#$%^&*()\\[\\]{}<>:;/\\\\|~`\"'-+=]".toRegex()
}