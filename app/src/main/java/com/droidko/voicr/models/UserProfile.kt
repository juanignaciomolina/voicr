package com.droidko.voicr.models

import java.util.*

data class UserProfile(
        val name: String = "",
        val avatarUrl: String = "",
        val subscriptionsCount: Int = 0,
        val timestamp: Long = System.currentTimeMillis() / 1000L) {

    fun toFbMap() : HashMap<String, Any> {
        var mappedUser: HashMap<String, Any> = HashMap()
        mappedUser.put("name", name)
        mappedUser.put("avatarUrl", avatarUrl)
        mappedUser.put("subscriptionsCount", subscriptionsCount)
        mappedUser.put("timestamp", timestamp)
        return mappedUser
    }

}