package com.droidko.voicr.producers.audio

import com.droidko.voicr.mcp.iMcpConsumer

interface iAudioRecordOutput : iMcpConsumer {

    fun onRecordFailure()

    fun onRecordSuccessful(pathToAudioRecord: String)

}