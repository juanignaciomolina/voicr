package com.droidko.voicr.views.home

import com.droidko.voicr.model.Message
import com.droidko.voicr.mvp.iMvpView

interface iHomeView : iMvpView {

    fun onMessageArrive(message: Message)
}