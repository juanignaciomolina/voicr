package com.droidko.voicr.presenters.auth.signup

import com.droidko.voicr.presenters.auth.iAuthOutput
import com.google.firebase.auth.FirebaseUser

interface iAuthSignupOutput {

    /**
     * Callback triggered after a successful user account creation
     * @param user: A [FirebaseUser] corresponding with the created and now logged user
     */
    fun onSignUpSuccessful(user: FirebaseUser)

    /**
     * Callback triggered after an unsuccessful user account creation
     * @param error: An instance of a [iAuthOutput.AuthError] specifying the error occurred during the signup
     */
    fun onSignUpFailure(error: iAuthOutput.AuthError)

}