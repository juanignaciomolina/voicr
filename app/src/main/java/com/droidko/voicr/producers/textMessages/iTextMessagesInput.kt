package com.droidko.voicr.producers.textMessages

import com.droidko.voicr.mcp.iMcpProducer

interface iTextMessagesInput : iMcpProducer {

    fun sendMessage(message: String)

    fun startListeningForMessages()

    fun stopListeningForMessages()
}