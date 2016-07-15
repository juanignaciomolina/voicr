package com.droidko.voicr.presenters.auth.signup

import com.droidko.voicr.presenters.auth.iAuthOutput
import com.google.firebase.auth.FirebaseUser

interface iAuthSignupOutput {

    fun onSignUpSuccessful(user: FirebaseUser)

    fun onSignUpFailure(error: iAuthOutput.AuthError)

}