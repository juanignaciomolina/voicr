package com.droidko.voicr.presenters.auth

import com.droidko.voicr.mvp.iMvpPresenter

interface iAuthPresenter : iMvpPresenter {

    fun doSignUp(email: String, password: String, confirmPassword: String)

    fun doLogin(email: String, password: String)

}