package com.droidko.voicr.presenters.newChannel

import com.droidko.voicr.emvp.iEmvpInput

interface iNewChannelInput: iEmvpInput {

    fun createChannel(channelName: String)

}