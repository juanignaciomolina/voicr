package com.droidko.voicr.presenters.audioPost.record

import com.droidko.voicr.emvp.iEmvpOutput

interface iAudioPostRecordOutput : iEmvpOutput {

    /**
     * Callback triggered after a successful creation of an [AudioPost]
     * @param pathToAudioRecord: An [String] with the local path to the audio file (not the audio URL on the server)
     */
    fun onRecordSuccessful(pathToAudioRecord: String)

    /**
     * Callback triggered after a failure during the [AudioPost] recording
     */
    fun onRecordFailure()

}