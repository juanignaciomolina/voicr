package com.droidko.voicr.presenters.editUserProfile

import com.droidko.voicr.models.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.jetbrains.anko.error

class EditUserProfilePresenter(val output: iEditUserProfileOutput): iEditUserProfileInput {
    val database = FirebaseDatabase.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser!!.uid

    override fun newUserProfile(newUserProfile: UserProfile) {
        database.reference
                .child("/user-profile/$uid/")
                .setValue(newUserProfile)
                .addOnSuccessListener { output.onUserProfileCreationSuccessful(newUserProfile) }
                .addOnFailureListener { exception -> output.onUserProfileCreationFailure(exception) }
    }

    override fun addSubscription(channelId: String) {
        database.reference
                .child("/user-profile/$uid/")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val userProfile = dataSnapshot.getValue(UserProfile::class.java)

                        //Check if the user was already subscribed to the channel
                        if (userProfile.subscriptions.contains(channelId)) {
                            output.onUserModificationSuccessful(userProfile)
                        } else {
                            userProfile.subscriptions.add(channelId)

                            //Update user's profile on the server
                            database.reference
                                    .child("/user-profile/$uid/")
                                    .setValue(userProfile)
                                    .addOnSuccessListener { output.onUserModificationSuccessful(userProfile) }
                                    .addOnFailureListener { exception ->
                                        error { "An error occurred while updating the user's profile: $exception" }
                                        output.onUserProfileCreationFailure(exception)
                                    }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        error { "An error occurred while retrieving the user's profile: $databaseError" }
                        output.onUserModificationFailure(databaseError.toException())
                    }
                })
    }

    override fun removeSubscription(channelId: String) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editAvatarUrl(avatarUrl: String) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editUsername(username: String) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}