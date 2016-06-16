package com.droidko.voicr.producers.auth

import com.droidko.voicr.mcp.iMcpProducer

interface iAuthInput : iMcpProducer {

    fun doSignUp(email: String, password: String, confirmPassword: String)

    fun doLogin(email: String, password: String)

}