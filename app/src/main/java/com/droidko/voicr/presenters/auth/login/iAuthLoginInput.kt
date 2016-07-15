package com.droidko.voicr.presenters.auth.login

import com.droidko.voicr.emvp.iEmvpInput

interface iAuthLoginInput: iEmvpInput {

    fun doLogin(email: String, password: String)

}