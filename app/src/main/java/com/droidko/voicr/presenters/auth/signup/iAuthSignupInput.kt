package com.droidko.voicr.presenters.auth.signup

import com.droidko.voicr.emvp.iEmvpInput

interface iAuthSignupInput: iEmvpInput {

    /**
     * Attempt to create a new user account with an email and a password
     * @param email: A valid email address
     * @param password: A valid user password
     * @param confirmPassword: A confirmation of the [password], typically they should match
     */
    fun doSignUp(email: String, password: String, confirmPassword: String)

}