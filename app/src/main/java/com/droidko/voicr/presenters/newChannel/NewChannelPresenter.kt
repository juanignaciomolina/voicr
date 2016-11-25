package com.droidko.voicr.presenters.newChannel

import android.util.Log
import com.droidko.voicr.emvp.iEmvpPresenter
import com.droidko.voicr.firebase.DbAccess
import com.droidko.voicr.models.ChannelProfile
import com.droidko.voicr.models.ChannelSubs
import com.droidko.voicr.models.UserSubs
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
                .userSubscriptions()
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val userSubs = dataSnapshot.getValue(UserSubs::class.java)
                        sendChannelCreationRequest(channelName, userSubs)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        error { "An error occurred while retrieving the user's profile: $databaseError" }
                        output.onChannelCreationFailure(databaseError.toException())
                    }
                })
    }

    fun sendChannelCreationRequest(channelName: String, userSubs: UserSubs) {
        val cid = database.reference.child(DbAccess.PATH_CHANNEL_PROFILE).push().key
        val channelProfile = ChannelProfile(cid = cid, name = channelName, membersCount = 1)

        val channelSubs = ChannelSubs()
        channelSubs.members.add(uid) // Add current user to the channel that is being created
        userSubs.subscriptions.add(cid) // Add the new channel to the logged user subscriptions

        // Prepare an atomic multi-reference update
        val updates: HashMap<String, Any> = HashMap()
        updates.put("${DbAccess.PATH_CHANNEL_PROFILE}/$cid/", channelProfile.toFbMap())
        updates.put("${DbAccess.PATH_CHANNEL_SUBSCRIPTIONS}/$cid/", channelSubs.toFbMap())
        updates.put("${DbAccess.PATH_USER_SUBSCRIPTIONS}/$uid/", userSubs.toFbMap())

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