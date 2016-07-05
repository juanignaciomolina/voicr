package com.droidko.voicr.producers.newChannel

import com.droidko.voicr.mcp.iMcpOutput

interface iNewChannelOutput: iMcpOutput {

    fun onChannelCreationSuccessful(channelId: String, channelName: String)

    fun onChannelCreationFailure(exception: Exception)

}