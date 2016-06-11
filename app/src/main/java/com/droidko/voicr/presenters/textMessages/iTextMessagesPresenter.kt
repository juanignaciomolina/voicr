package com.droidko.voicr.presenters.textMessages

import com.droidko.voicr.mvp.iMvpPresenter

interface iTextMessagesPresenter : iMvpPresenter {

    fun sendMessage(message: String)

    fun startListeningForMessages()

    fun stopListeningForMessages()
}