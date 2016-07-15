package com.droidko.voicr.models

import java.util.*

// TODO Replace local time in millis with ServerValue.TIMESTAMP
// TODO Reference: https://firebase.google.com/docs/database/android/offline-capabilities

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
