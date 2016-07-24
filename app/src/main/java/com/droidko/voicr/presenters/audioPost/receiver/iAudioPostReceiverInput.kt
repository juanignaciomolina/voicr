package com.droidko.voicr.presenters.audioPost.receiver

import com.droidko.voicr.emvp.iEmvpInput

interface iAudioPostReceiverInput: iEmvpInput {

    /**
     * Start receiving updates from a given audio channel
     * @param channelId: A channel ID
     */
    fun startListeningToChannel(channelId: String)

    /**
     * Stop receiving updates from a given audio channel
     * @param channelId: A channel ID
     */
    fun stopListeningFromChannel(channelId: String)

}