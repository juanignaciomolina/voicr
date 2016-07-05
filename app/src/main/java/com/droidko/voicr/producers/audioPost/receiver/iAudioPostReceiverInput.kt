package com.droidko.voicr.producers.audioPost.receiver

import com.droidko.voicr.mcp.iMcpInput

interface iAudioPostReceiverInput: iMcpInput {

    fun subsribeToChannel(channelId: String)

    fun unsubscribeFromChannel(channelId: String)

}