package com.droidko.voicr.presenters.editUserProfile

import com.droidko.voicr.emvp.iEmvpPresenter
import com.droidko.voicr.firebase.DbAccess
import com.droidko.voicr.models.ChannelProfile
import com.droidko.voicr.models.UserProfile
import com.droidko.voicr.models.UserSubs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.jetbrains.anko.error
import java.util.*

class EditUserProfilePresenter(val output: iEditUserProfileOutput): iEmvpPresenter, iEditUserProfileInput {

    val database = FirebaseDatabase.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser!!.uid

    override fun newUser() {
        val newUserProfile = UserProfile(uid) // New empty user profile
        val newUserSubs = UserSubs() // New empty user subscriptions

        // Prepare an atomic multi-reference update
        val updates: HashMap<String, Any> = HashMap()
        updates.put("${DbAccess.PATH_USER_PROFILE}/$uid/", newUserProfile.toFbMap())
        updates.put("${DbAccess.PATH_USER_SUBSCRIPTIONS}/$uid/", newUserSubs.toFbMap())

        database.reference
                .updateChildren(updates)
                .addOnSuccessListener { output.onUserCreationSuccessful(newUserProfile, newUserSubs) }
                .addOnFailureListener { exception -> output.onUserCreationFailure(exception) }
    }

    private fun updateUserProfile(userProfile: UserProfile) {
        //Update user's profile on the server
        dbAccess()
                .userProfile()
                .setValue(userProfile)
                .addOnSuccessListener { output.onUserProfileModificationSuccessful(userProfile) }
                .addOnFailureListener { exception ->
                    error { "An error occurred while updating the user's profile: $exception" }
                    output.onUserCreationFailure(exception)
                }
    }

    private fun updateUserSubs(userSubs: UserSubs) {
        //Update user's subscriptions on the server
        dbAccess()
                .userSubscriptions()
                .setValue(userSubs)
                .addOnSuccessListener { output.onUserSubsModificationSuccessful(userSubs) }
                .addOnFailureListener { exception ->
                    error { "An error occurred while updating the user's subscriptions: $exception" }
                    output.onUserSubsModificationFailure(exception)
                }
    }

    override fun addSubscription(channelProfile: ChannelProfile) {
        dbAccess()
                .userProfile()
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val userSubs = dataSnapshot.getValue(UserSubs::class.java)

                        //Check if the user was already subscribed to the channel
                        if (userSubs.subscriptions.contains(channelProfile.cid)) {
                            output.onUserSubsModificationSuccessful(userSubs)
                        } else {
                            userSubs.subscriptions.put(channelProfile.cid, channelProfile)
                            updateUserSubs(userSubs)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        error { "An error occurred while retrieving the user's profile: $databaseError" }
                        output.onUserProfileModificationFailure(databaseError.toException())
                    }
                })
    }

    override fun removeSubscription(channelProfile: ChannelProfile) {
        dbAccess()
                .userSubscriptions()
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val userSubs = dataSnapshot.getValue(UserSubs::class.java)

                        //Check if the user was already unsubscribed to the channel
                        if (!userSubs.subscriptions.contains(channelProfile.cid)) {
                            output.onUserSubsModificationSuccessful(userSubs)
                        } else {
                            userSubs.subscriptions.remove(channelProfile.cid)
                            updateUserSubs(userSubs)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        error { "An error occurred while retrieving the user's profile: $databaseError" }
                        output.onUserProfileModificationFailure(databaseError.toException())
                    }
                })
    }

    override fun editAvatarUrl(avatarUrl: String) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editUsername(username: String) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}