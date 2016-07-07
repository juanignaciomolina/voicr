package com.droidko.voicr.presenters.newChannel

import com.droidko.voicr.emvp.iEmvpOutput

interface iNewChannelOutput: iEmvpOutput {

    fun onChannelCreationSuccessful(channelId: String, channelName: String)

    fun onChannelCreationFailure(exception: Exception)

}