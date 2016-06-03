package com.droidko.voicr.presenters.home

import com.droidko.voicr.mvp.iMvpPresenter

interface iHomePresenter: iMvpPresenter {

    fun sendMessage(message: String)

}