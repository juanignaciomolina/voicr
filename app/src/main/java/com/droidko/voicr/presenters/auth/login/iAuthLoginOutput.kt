package com.droidko.voicr.presenters.auth.login

import com.droidko.voicr.presenters.auth.iAuthOutput
import com.google.firebase.auth.FirebaseUser

interface iAuthLoginOutput {

    /**
     * Callback after a successful login attempt
     * @param user: A [FirebaseUser] corresponding with the logged user
     */
    fun onLoginSuccessful(user: FirebaseUser)

    /**
     * Callback after an unsuccessful login attempt
     * @param error: An instance of a [iAuthOutput.AuthError] specifying the error occurred during the login
     */
    fun onLoginFailure(error: iAuthOutput.AuthError)

}