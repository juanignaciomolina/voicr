package com.droidko.voicr.presenters.audioPost.receiver

import com.droidko.voicr.emvp.iEmvpInput

interface iAudioPostReceiverInput: iEmvpInput {

    fun subsribeToChannel(channelId: String)

    fun unsubscribeFromChannel(channelId: String)

}