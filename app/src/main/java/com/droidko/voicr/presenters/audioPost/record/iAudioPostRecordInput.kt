package com.droidko.voicr.presenters.audioPost.record

import com.droidko.voicr.emvp.iEmvpInput

interface iAudioPostRecordInput : iEmvpInput {

    fun startRecording()

    fun stopRecording()

}