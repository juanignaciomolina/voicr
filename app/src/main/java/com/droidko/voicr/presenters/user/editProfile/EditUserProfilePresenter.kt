package com.droidko.voicr.presenters.user.editProfile

import com.droidko.voicr.emvp.iEmvpPresenter
import com.droidko.voicr.firebase.DbAccess
import com.droidko.voicr.models.user.UserProfile
import com.droidko.voicr.models.user.UserSubs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.error
import java.util.*

class EditUserProfilePresenter(val output: iEditUserProfileOutput): iEmvpPresenter, iEditUserProfileInput {

    private val database = FirebaseDatabase.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser!!.uid

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

    override fun editAvatarUrl(avatarUrl: String) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editUsername(username: String) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}