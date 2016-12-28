package com.droidko.voicr.models.user

import com.droidko.voicr.models.channel.ChannelProfile
import java.util.*

data class UserSubs(
        val subscriptions: HashMap<String, ChannelProfile> = HashMap(),
        val timestamp: Long = System.currentTimeMillis() / 1000L) {

    fun toFbMap() : HashMap<String, Any> {
        val mappedSubs: HashMap<String, Any> = HashMap()
        mappedSubs.put("subscriptions", subscriptions)
        mappedSubs.put("timestamp", timestamp)
        return mappedSubs
    }


}
