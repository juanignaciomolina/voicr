package com.droidko.voicr.producers.audioPost.receiver

import com.droidko.voicr.mcp.iMcpOutput
import com.droidko.voicr.model.AudioPost

interface iAudioPostReceiverOutput: iMcpOutput {

    fun onAudioPostReceived(post: AudioPost, new: Boolean)

}