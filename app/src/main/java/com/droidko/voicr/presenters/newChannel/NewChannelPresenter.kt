package com.droidko.voicr.presenters.newChannel

import android.util.Log
import com.droidko.voicr.models.Channel
import com.droidko.voicr.models.UserProfile
import com.droidko.voicr.presenters.uploads.FileUploadService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class NewChannelPresenter(val output: iNewChannelOutput): iNewChannelInput {

    val database = FirebaseDatabase.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser!!.uid

    override fun createChannel(channelName: String) {
        val newChannel = Channel(channelName)
        val newUser = UserProfile("Juani") // TODO UNHARDCODE THIS, DONT CREATE USERS HERE
        val cid = database.reference.child("/channel/").push().key

        // Add current userProfile to the channel that is being created
        newChannel.members.add(uid)
        newUser.subscriptions.add(cid)

        // Prepare an atomic multi-reference update
        var updates: HashMap<String, Any> = HashMap()
        updates.put("/channel/$cid/", newChannel.toFbMap())
        updates.put("/user-profile/$uid/subscriptions/", newUser.subscriptions)

        database.reference
                .updateChildren(updates)
                .addOnSuccessListener {
                    Log.i(FileUploadService.TAG, "Channel created with id: $cid")
                    output.onChannelCreationSuccessful(channelName, cid)
                }
                .addOnFailureListener { exception ->
                    Log.e(FileUploadService.TAG, "Failure: $exception")
                    output.onChannelCreationFailure(exception)
                }
    }

}