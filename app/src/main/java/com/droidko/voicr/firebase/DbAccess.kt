package com.droidko.voicr.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DbAccess {

    // Public paths
    companion object {
        val PATH_USER_PROFILE = "/user-profile"
        val PATH_CHANNEL = "/channel"
        val PATH_CHANNEL_POSTS = "/channel-posts"
        val PATH_USER_POSTS = "/user-posts"
    }

    val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
    val loggedUserId: String = FirebaseAuth.getInstance().currentUser!!.uid

    fun userProfile(uid: String = loggedUserId): DatabaseReference {
        return reference.child("$PATH_USER_PROFILE/$uid/")
    }

    fun channel(cid: String): DatabaseReference {
        return reference.child("$PATH_CHANNEL/$cid/")
    }

    fun userPosts(uid: String = loggedUserId): DatabaseReference {
        return reference.child("$PATH_USER_POSTS/$uid/")
    }

    fun channelPosts(cid: String): DatabaseReference {
        return reference.child("$PATH_CHANNEL_POSTS/$cid/")
    }
}