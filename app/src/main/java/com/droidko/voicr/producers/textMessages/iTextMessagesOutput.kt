package com.droidko.voicr.producers.textMessages

import com.droidko.voicr.mcp.iMcpConsumer
import com.droidko.voicr.model.Message

interface iTextMessagesOutput : iMcpConsumer {

    fun onMessageArrive(message: Message)
}