package com.example.chatapp.common

object MessageSelector {
    fun getMessageFromCode(errorCode: String?): String {
        return when (errorCode) {
            FirebaseAuthErrorCodes.ERROR_INVALID_EMAIL -> "The email address is badly formatted."
            FirebaseAuthErrorCodes.ERROR_WRONG_PASSWORD -> "The password is incorrect. Please try again."
            FirebaseAuthErrorCodes.ERROR_USER_MISMATCH -> "The supplied credentials do not correspond to the previously signed-in user."
            FirebaseAuthErrorCodes.ERROR_REQUIRES_RECENT_LOGIN -> "This operation is sensitive and requires recent authentication. Log in again before retrying this request."
            FirebaseAuthErrorCodes.ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL -> "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address."
            FirebaseAuthErrorCodes.ERROR_EMAIL_ALREADY_IN_USE -> "The email address is already in use by another account."
            FirebaseAuthErrorCodes.ERROR_CREDENTIAL_ALREADY_IN_USE -> "This credential is already associated with a different user account."
            FirebaseAuthErrorCodes.ERROR_USER_DISABLED -> "The user account has been disabled by an administrator."
            FirebaseAuthErrorCodes.ERROR_USER_TOKEN_EXPIRED -> "The user's credential is no longer valid. The user must sign in again."
            FirebaseAuthErrorCodes.ERROR_USER_NOT_FOUND -> "There is no user record corresponding to this identifier. The user may have been deleted."
            FirebaseAuthErrorCodes.ERROR_INVALID_USER_TOKEN -> "The user's credential is no longer valid. The user must sign in again."
            FirebaseAuthErrorCodes.ERROR_OPERATION_NOT_ALLOWED -> "This operation is not allowed. You must enable this service in the console."
            FirebaseAuthErrorCodes.ERROR_WEAK_PASSWORD -> "The password is not strong enough."
            FirebaseAuthErrorCodes.ERROR_TOO_MANY_REQUESTS -> "Access to this account has been temporarily disabled due to many failed login attempts. You can immediately restore it by resetting your password or you can try again later."
            FirebaseAuthErrorCodes.ERROR_INVALID_CREDENTIAL -> "Invalid mail or password. Please try again."
            FirebaseAuthErrorCodes.ERROR_INVALID_CUSTOM_TOKEN -> "The custom token format is incorrect. Please check the documentation."
            FirebaseAuthErrorCodes.ERROR_CUSTOM_TOKEN_MISMATCH -> "The custom token corresponds to a different Firebase project."
            FirebaseAuthErrorCodes.ERROR_INVALID_PASSWORD -> "The password is invalid or the user does not have a password."
            FirebaseAuthErrorCodes.ERROR_INVALID_VERIFICATION_CODE -> "The SMS verification code used to create the phone auth credential is invalid. Please resend the verification code sms and be sure use the verification code provided by the user."
            FirebaseAuthErrorCodes.ERROR_INVALID_VERIFICATION_ID -> "The verification ID used to create the phone auth credential is invalid."
            else -> "An unexpected error occurred. Please try again."
        }
    }
}
