package com.droidko.voicr.producers.auth

import com.droidko.voicr.mcp.iMcpInput

interface iAuthInput : iMcpInput {

    fun doSignUp(email: String, password: String, confirmPassword: String)

    fun doLogin(email: String, password: String)

}