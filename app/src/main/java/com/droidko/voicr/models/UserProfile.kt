package com.droidko.voicr.models

import java.util.*

data class UserProfile(
        val name: String,
        val avatarUrl: String = "",
        val subscriptions: ArrayList<String> = ArrayList(),
        val timestamp: Long = System.currentTimeMillis() / 1000L) {

    fun toFbMap() : HashMap<String, Any> {
        var mappedUser: HashMap<String, Any> = HashMap()
        mappedUser.put("name", name)
        mappedUser.put("avatarUrl", avatarUrl)
        mappedUser.put("subscriptions", subscriptions)
        mappedUser.put("timestamp", timestamp)
        return mappedUser
    }

}