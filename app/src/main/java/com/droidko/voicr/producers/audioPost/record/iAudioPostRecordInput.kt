package com.droidko.voicr.producers.audioPost.record

import com.droidko.voicr.mcp.iMcpProducer

interface iAudioPostRecordInput : iMcpProducer {

    fun startRecording()

    fun stopRecording()

}