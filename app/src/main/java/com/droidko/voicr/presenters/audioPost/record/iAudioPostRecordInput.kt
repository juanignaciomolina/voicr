package com.droidko.voicr.presenters.audioPost.record

import com.droidko.voicr.emvp.iEmvpInput

interface iAudioPostRecordInput : iEmvpInput {

    /**
     * Start recording an AudioPost and upload it to an audio channel
     */
    fun startRecording()

    /**
     * Stop recording an AudioPost that was being recorded with startRecording()
     */
    fun stopRecording()

}