package com.droidko.voicr.presenters.newChannel

import com.droidko.voicr.emvp.iEmvpInput

interface iNewChannelInput: iEmvpInput {

    /**
     * Creates a new [Channel] server side
     * @param channelName: A name for the new channel (this is not a channel ID, it will be created automatically)
     */
    fun createChannel(channelName: String)

}