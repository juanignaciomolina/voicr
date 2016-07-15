package com.droidko.voicr.presenters.newChannel

import android.util.Log
import com.droidko.voicr.emvp.iEmvpPresenter
import com.droidko.voicr.firebase.DbAccess
import com.droidko.voicr.models.Channel
import com.droidko.voicr.models.UserProfile
import com.droidko.voicr.presenters.uploads.FileUploadService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.jetbrains.anko.error
import java.util.*

class NewChannelPresenter(val output: iNewChannelOutput): iEmvpPresenter, iNewChannelInput {

    val database = FirebaseDatabase.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser!!.uid

    override fun createChannel(channelName: String) {

        // We first need access to the user's profile so that we can subscribe him to the new channel later
        dbAccess()
                .userProfile()
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val userProfile = dataSnapshot.getValue(UserProfile::class.java)
                        sendChannelCreationRequest(channelName, userProfile)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        error { "An error occurred while retrieving the user's profile: $databaseError" }
                        output.onChannelCreationFailure(databaseError.toException())
                    }
                })
    }

    fun sendChannelCreationRequest(channelName: String, userProfile: UserProfile) {
        val cid = database.reference.child(DbAccess.PATH_CHANNEL).push().key
        val newChannel = Channel(channelName)
        newChannel.members.add(uid) // Add current user to the channel that is being created

        // Prepare an atomic multi-reference update
        val updates: HashMap<String, Any> = HashMap()
        updates.put("${DbAccess.PATH_CHANNEL}/$cid/", newChannel.toFbMap())
        updates.put("${DbAccess.PATH_USER_PROFILE}/$uid/", userProfile.toFbMap())

        database.reference
                .updateChildren(updates)
                .addOnSuccessListener {
                    output.onChannelCreationSuccessful(cid, channelName)
                }
                .addOnFailureListener { exception ->
                    Log.e(FileUploadService.TAG, "Failure: $exception")
                    output.onChannelCreationFailure(exception)
                }
    }

}