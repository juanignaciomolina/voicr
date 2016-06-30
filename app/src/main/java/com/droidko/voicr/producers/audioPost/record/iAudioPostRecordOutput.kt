package com.droidko.voicr.producers.audioPost.record

import com.droidko.voicr.mcp.iMcpConsumer

interface iAudioPostRecordOutput : iMcpConsumer {

    fun onRecordFailure()

    fun onRecordSuccessful(pathToAudioRecord: String)

}