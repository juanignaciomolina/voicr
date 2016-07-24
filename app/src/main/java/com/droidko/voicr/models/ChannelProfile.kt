package com.droidko.voicr.models

import java.util.*

// TODO Replace local time in millis with ServerValue.TIMESTAMP
// TODO Reference: https://firebase.google.com/docs/database/android/offline-capabilities

data class ChannelProfile(
        val name: String,
        val membersCount: Int,
        val timestamp: Long = System.currentTimeMillis() / 1000L) {

    fun toFbMap() : HashMap<String, Any> {
        val mappedChannel: HashMap<String, Any> = HashMap()
        mappedChannel.put("name", name)
        mappedChannel.put("membersCount", membersCount)
        mappedChannel.put("timestamp", timestamp)
        return mappedChannel
    }


}
