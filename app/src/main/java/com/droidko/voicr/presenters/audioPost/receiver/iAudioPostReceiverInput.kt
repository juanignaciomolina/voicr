package com.droidko.voicr.presenters.audioPost.receiver

import com.droidko.voicr.emvp.iEmvpInput

interface iAudioPostReceiverInput: iEmvpInput {
    
    fun startListeningToChannel(channelId: String)

    fun stopListeningFromChannel(channelId: String)

}