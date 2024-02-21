package com.example.chatapp.data.utils

object FirestoreUtils {
    val SEARCH_NAME_PATTERN = "[ _.,?!@#$%^&*()\\[\\]{}<>:;/\\\\|~`\"'-+=]".toRegex()
}