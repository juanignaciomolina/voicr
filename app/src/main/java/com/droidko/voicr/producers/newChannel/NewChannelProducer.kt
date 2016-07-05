package com.droidko.voicr.producers.newChannel

import android.util.Log
import com.droidko.voicr.model.Channel
import com.droidko.voicr.model.User
import com.droidko.voicr.producers.uploads.FileUploadService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class NewChannelProducer(val output: iNewChannelOutput): iNewChannelInput {

    val database = FirebaseDatabase.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser!!.uid

    override fun createChannel(channelName: String) {
        val newChannel = Channel(channelName)
        val newUser = User("Juani") // TODO UNHARDCODE THIS, DONT CREATE USERS HERE
        val cid = database.reference.child("/channel/").push().key

        // Add current user to the channel that is being created
        newChannel.members.add(uid)
        newUser.subscriptions.add(cid)

        // Prepare an atomic multi-reference update
        var updates: HashMap<String, Any> = HashMap()
        updates.put("/channel/$cid/", newChannel.toFbMap())
        updates.put("/user/$uid/", newUser.toFbMap())

        database.reference
                .updateChildren(updates)
                .addOnSuccessListener {
                    Log.i(FileUploadService.TAG, "Channeld created with id: $cid")
                    output.onChannelCreationSuccessful(channelName, cid)
                }
                .addOnFailureListener { exception ->
                    Log.e(FileUploadService.TAG, "Failure: $exception")
                    output.onChannelCreationFailure(exception)
                }
    }

}