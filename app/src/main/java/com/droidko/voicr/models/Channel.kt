package com.droidko.voicr.models

import java.util.*

data class Channel(
        val name: String,
        val members: ArrayList<String> = ArrayList(),
        val timestamp: Long = System.currentTimeMillis() / 1000L) {

    fun toFbMap() : HashMap<String, Any> {
        var mappedChannel: HashMap<String, Any> = HashMap()
        mappedChannel.put("name", name)
        mappedChannel.put("members", members)
        mappedChannel.put("timestamp", timestamp)
        return mappedChannel
    }


}
