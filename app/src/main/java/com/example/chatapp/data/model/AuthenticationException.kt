package com.example.chatapp.data.model

class AuthenticationException(val errorCode: String?, message: String?): Exception(message)
