package com.droidko.voicr.models.post

import com.droidko.voicr.models.iFirebaseModel
import com.droidko.voicr.models.user.UserProfile
import java.util.*

abstract class Post(
        val user: UserProfile = UserProfile(),
        val timestamp: Long = System.currentTimeMillis() / 1000L) : iFirebaseModel {

    override fun toFbMap() : HashMap<String, Any> {
        var mappedPost: HashMap<String, Any> = HashMap()
        mappedPost.put("user", user)
        mappedPost.put("timestamp", timestamp)
        return mappedPost
    }

}