package com.droidko.voicr.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class StorageAccess {

    // Public paths
    companion object {
        val PATH_USER_STORAGE = "/user-storage"
        val PATH_CHANNEL_STORAGE = "/channel-storage"
    }

    // Private paths
    val TYPE_AUDIOS = "audios"

    val reference: StorageReference = FirebaseStorage.getInstance().reference
    val loggedUserId: String = FirebaseAuth.getInstance().currentUser!!.uid

    fun userAudios(uid: String = loggedUserId): StorageReference {
        return reference.child("$PATH_USER_STORAGE/$uid/$TYPE_AUDIOS/")
    }

    fun chanelAudios(cid: String): StorageReference {
        return reference.child("$PATH_CHANNEL_STORAGE/$cid/$TYPE_AUDIOS/")
    }

}