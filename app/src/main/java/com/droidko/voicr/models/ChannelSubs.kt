package com.droidko.voicr.models

import java.util.*

data class ChannelSubs(
        val members: ArrayList<String> = ArrayList(),
        val timestamp: Long = System.currentTimeMillis() / 1000L) {

    fun toFbMap() : HashMap<String, Any> {
        val mappedSubs: HashMap<String, Any> = HashMap()
        mappedSubs.put("members", members)
        mappedSubs.put("timestamp", timestamp)
        return mappedSubs
    }


}
