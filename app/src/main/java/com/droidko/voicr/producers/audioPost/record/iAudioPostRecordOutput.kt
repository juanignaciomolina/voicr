package com.droidko.voicr.producers.audioPost.record

import com.droidko.voicr.mcp.iMcpOutput

interface iAudioPostRecordOutput : iMcpOutput {

    fun onRecordFailure()

    fun onRecordSuccessful(pathToAudioRecord: String)

}