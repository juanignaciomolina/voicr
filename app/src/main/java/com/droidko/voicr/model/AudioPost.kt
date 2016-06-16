package com.droidko.voicr.model

import com.google.firebase.auth.FirebaseAuth
import java.util.*

data class AudioPost(
        val downloadUrl: String = "",
        val uid: String = FirebaseAuth.getInstance().currentUser!!.uid,
        val timestamp: Long = System.currentTimeMillis() / 1000L) {

    fun toFbMap() : HashMap<String, Any> {
        var mappedPost: HashMap<String, Any> = HashMap()
        mappedPost.put("downloadUrl", downloadUrl)
        mappedPost.put("uid", uid)
        mappedPost.put("timestamp", timestamp)
        return mappedPost
    }

}