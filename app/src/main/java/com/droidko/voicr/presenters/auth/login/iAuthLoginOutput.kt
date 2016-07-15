package com.droidko.voicr.presenters.auth.login

import com.droidko.voicr.presenters.auth.iAuthOutput
import com.google.firebase.auth.FirebaseUser

interface iAuthLoginOutput {

    fun onLoginSuccessful(user: FirebaseUser)

    fun onLoginFailure(error: iAuthOutput.AuthError)

}