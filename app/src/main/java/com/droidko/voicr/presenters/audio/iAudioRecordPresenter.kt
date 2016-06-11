package com.droidko.voicr.presenters.audio

import com.droidko.voicr.mvp.iMvpPresenter

interface iAudioRecordPresenter: iMvpPresenter {

    fun startRecording()

    fun stopRecording()

}