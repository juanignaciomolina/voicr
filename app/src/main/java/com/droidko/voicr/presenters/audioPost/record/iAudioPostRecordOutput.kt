package com.droidko.voicr.presenters.audioPost.record

import com.droidko.voicr.emvp.iEmvpOutput

interface iAudioPostRecordOutput : iEmvpOutput {

    fun onRecordFailure()

    fun onRecordSuccessful(pathToAudioRecord: String)

}