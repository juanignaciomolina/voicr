package com.droidko.voicr.presenters.channel.newChannel

import com.droidko.voicr.emvp.iEmvpOutput

interface iNewChannelOutput: iEmvpOutput {

    /**
     * Callback triggered after a successful server side channel creation
     * @param channelId: The ID of the new channel
     * @param channelName: The name of the new channel
     */
    fun onChannelCreationSuccessful(channelId: String, channelName: String)

    /**
     * Callback triggered after an unsuccessful server side channel creation
     * @param exception: An [Exception] specifying the error that occurred during the channel creation
     */
    fun onChannelCreationFailure(exception: Exception)

}