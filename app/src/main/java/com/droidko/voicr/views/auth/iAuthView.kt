package com.droidko.voicr.views.auth

import com.droidko.voicr.mvp.iMvpView
import com.google.firebase.auth.FirebaseUser

interface iAuthView : iMvpView {

    enum class AuthError {
        SERVER_REJECTED_CREDENTIALS, // Wrong username or password
        INVALID_EMAIL, // Must be a valid email adress
        PASSWORD_TOO_SHORT, // Password is too short
        PASSWORDS_DONT_MATCH, // Password and confirmPassword do not match
        NETWORK, // Network error
        EMAIL_TAKEN, // The selected email is already taken
        EMAIL_NOT_REGISTERED, // The selected email is not registered in the system
        UNKNOWN // An unknown error
    }

    fun onSignUpSuccessful(user: FirebaseUser)

    fun onSignUpFailure(error: AuthError)

    fun onLoginSuccessful(user: FirebaseUser)

    fun onLoginFailure(error: AuthError)

}