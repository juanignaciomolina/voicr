package com.droidko.voicr.producers.audioPost.record

import com.droidko.voicr.mcp.iMcpInput

interface iAudioPostRecordInput : iMcpInput {

    fun startRecording()

    fun stopRecording()

}