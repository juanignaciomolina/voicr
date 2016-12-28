package com.droidko.voicr.models.post

import com.droidko.voicr.models.user.UserProfile
import java.util.*

class AudioPost(
        val downloadUrl: String = "",
        user: UserProfile = UserProfile() ,
        timestamp: Long = System.currentTimeMillis() / 1000L) : Post(user = user, timestamp = timestamp)
{

    override fun toFbMap() : HashMap<String, Any> {
        val mappedPost = super.toFbMap()
        mappedPost.put("downloadUrl", downloadUrl)
        return mappedPost
    }

}