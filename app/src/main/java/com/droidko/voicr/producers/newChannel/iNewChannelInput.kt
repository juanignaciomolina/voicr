package com.droidko.voicr.producers.newChannel

import com.droidko.voicr.mcp.iMcpInput

interface iNewChannelInput: iMcpInput {

    fun createChannel(channelName: String)

}