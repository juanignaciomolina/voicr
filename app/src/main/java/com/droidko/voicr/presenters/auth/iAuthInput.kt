package com.droidko.voicr.presenters.auth

import com.droidko.voicr.emvp.iEmvpInput

interface iAuthInput : iEmvpInput {

    fun doSignUp(email: String, password: String, confirmPassword: String)

    fun doLogin(email: String, password: String)

}