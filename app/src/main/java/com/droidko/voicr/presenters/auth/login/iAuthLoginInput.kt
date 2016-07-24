package com.droidko.voicr.presenters.auth.login

import com.droidko.voicr.emvp.iEmvpInput

interface iAuthLoginInput: iEmvpInput {

    /**
     * Log in a user with its email address and password
     * @param email: A valid email address
     * @param password: A user password
     */
    fun doLogin(email: String, password: String)

}