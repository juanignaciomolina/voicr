package com.droidko.voicr.producers.audio

import com.droidko.voicr.mcp.iMcpProducer

interface iAudioRecordInput : iMcpProducer {

    fun startRecording()

    fun stopRecording()

}