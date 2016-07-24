package com.droidko.voicr.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DbAccess {

    // Public paths
    companion object {
        val PATH_USER_PROFILE = "/user-profile"
        val PATH_USER_POSTS = "/user-posts"
        val PATH_USER_SUBSCRIPTIONS = "/user-subs"
        val PATH_CHANNEL_PROFILE = "/channel-profile"
        val PATH_CHANNEL_POSTS = "/channel-posts"
        val PATH_CHANNEL_SUBSCRIPTIONS = "/channel-subs"
    }

    val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
    val loggedUserId: String = FirebaseAuth.getInstance().currentUser!!.uid

    fun userProfile(uid: String = loggedUserId): DatabaseReference {
        return reference.child("$PATH_USER_PROFILE/$uid/")
    }

    fun userPosts(uid: String = loggedUserId): DatabaseReference {
        return reference.child("$PATH_USER_POSTS/$uid/")
    }

    fun userSubscriptions(uid: String = loggedUserId): DatabaseReference {
        return reference.child("$PATH_USER_SUBSCRIPTIONS/$uid/")
    }

    fun channelProfile(cid: String): DatabaseReference {
        return reference.child("$PATH_CHANNEL_PROFILE/$cid/")
    }

    fun channelPosts(cid: String): DatabaseReference {
        return reference.child("$PATH_CHANNEL_POSTS/$cid/")
    }

    fun channelSubscriptions(cid: String): DatabaseReference {
        return reference.child("$PATH_CHANNEL_SUBSCRIPTIONS/$cid/")
    }
}