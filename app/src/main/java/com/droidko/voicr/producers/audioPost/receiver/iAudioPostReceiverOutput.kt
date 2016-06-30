package com.droidko.voicr.producers.audioPost.receiver

import com.droidko.voicr.mcp.iMcpConsumer
import com.droidko.voicr.model.AudioPost

interface iAudioPostReceiverOutput: iMcpConsumer {

    fun onAudioPostReceived(post: AudioPost, new: Boolean)

}