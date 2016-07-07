package com.droidko.voicr.presenters.audioPost.receiver

import com.droidko.voicr.emvp.iEmvpOutput
import com.droidko.voicr.models.AudioPost

interface iAudioPostReceiverOutput: iEmvpOutput {

    fun onAudioPostReceived(post: AudioPost, new: Boolean)

}