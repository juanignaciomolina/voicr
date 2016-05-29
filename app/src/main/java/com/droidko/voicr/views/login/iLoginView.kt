package com.droidko.voicr.views.login

import com.droidko.voicr.mvp.iMvpView

interface iLoginView : iMvpView {

    fun onLoginSuccessful()

    fun onLoginFailure()

}