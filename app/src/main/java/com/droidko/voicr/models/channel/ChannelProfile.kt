package com.droidko.voicr.models.channel

import com.droidko.voicr.models.post.AudioPost
import java.util.*

// TODO Replace local time in millis with ServerValue.TIMESTAMP
// TODO Reference: https://firebase.google.com/docs/database/android/offline-capabilities

data class ChannelProfile(
        val cid: String = "",
        val name: String = "",
        val membersCount: Int = 0,
        val avatar: String = "",
        val lastPost: AudioPost? = null,
        val timestamp: Long = System.currentTimeMillis() / 1000L) {

    fun toFbMap() : HashMap<String, Any> {
        val mappedChannel: HashMap<String, Any> = HashMap()
        mappedChannel.put("cid", cid)
        mappedChannel.put("name", name)
        mappedChannel.put("avatar", avatar)
        mappedChannel.put("membersCount", membersCount)
        lastPost?.let { mappedChannel.put("lastPost", it) }
        mappedChannel.put("timestamp", timestamp)
        return mappedChannel
    }


}
