package com.droidko.voicr.presenters.audio

import com.droidko.voicr.mvp.iMvpView

interface iAudioRecordView: iMvpView {

    fun onRecordFailure()

    fun onRecordSuccessful(pathToAudioRecord: String)

}