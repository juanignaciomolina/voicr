package com.droidko.voicr.presenters.auth

abstract class AuthPresenter(val authOutput: iAuthOutput){

    // Constants
    companion object {
        const val MIN_PASSWORD_LENGTH = 8
    }

}