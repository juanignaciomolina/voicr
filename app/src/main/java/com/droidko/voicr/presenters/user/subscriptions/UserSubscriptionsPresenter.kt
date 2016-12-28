package com.droidko.voicr.presenters.user.subscriptions

import com.droidko.voicr.emvp.iEmvpPresenter
import com.droidko.voicr.models.channel.ChannelProfile
import com.droidko.voicr.models.user.UserSubs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.jetbrains.anko.error
import java.util.*

open class UserSubscriptionsPresenter(val output: iUserSubscriptionsOutput): iEmvpPresenter, iUserSubscriptionsInput {

    private val activeSubscriptionsListeners: ArrayList<ChildEventListener> = ArrayList()

    private val uid = FirebaseAuth.getInstance().currentUser!!.uid

    override fun startListeningForUserSubscriptions(userId: String, childEventListener: ChildEventListener) {
        dbAccess()
                .userSubscriptions(userId)
                .addChildEventListener(childEventListener)

        activeSubscriptionsListeners.add(childEventListener)
    }

    override fun startListeningForUserSubscriptions(childEventListener: ChildEventListener) {
        startListeningForUserSubscriptions(uid, childEventListener)
    }

    override fun getUserSubscriptions(userId: String) {
        dbAccess()
                .userSubscriptions(userId)
                .addListenerForSingleValueEvent( object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        output.onUserSubsGetFailure(error.toException())
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        output.onUserSubsGetSuccessful(dataSnapshot.getValue(UserSubs::class.java))
                    }
                })
    }

    override fun getUserSubscriptions() {
        getUserSubscriptions(uid)
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
                        output.onUserSubsModificationFailure(databaseError.toException())
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
                        output.onUserSubsModificationFailure(databaseError.toException())
                    }
                })
    }

}