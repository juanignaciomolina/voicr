package com.droidko.voicr.presenters.login

import com.droidko.voicr.mvp.iMvpPresenter

interface iLoginPresenter : iMvpPresenter {

    fun doLogin(email: String, password: String, confirmPassword: String)

}