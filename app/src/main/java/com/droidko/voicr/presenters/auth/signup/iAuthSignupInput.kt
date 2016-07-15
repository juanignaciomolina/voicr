package com.droidko.voicr.presenters.auth.signup

import com.droidko.voicr.emvp.iEmvpInput

interface iAuthSignupInput: iEmvpInput {

    fun doSignUp(email: String, password: String, confirmPassword: String)

}