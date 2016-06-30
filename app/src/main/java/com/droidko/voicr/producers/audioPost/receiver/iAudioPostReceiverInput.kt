package com.droidko.voicr.producers.audioPost.receiver

import com.droidko.voicr.mcp.iMcpProducer

interface iAudioPostReceiverInput: iMcpProducer {

    fun subsribeToChannel(channelId: String)

    fun unsubscribeFromChannel(channelId: String)

}