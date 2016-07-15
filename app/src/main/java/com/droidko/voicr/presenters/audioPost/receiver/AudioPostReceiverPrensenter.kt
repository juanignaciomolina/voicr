package com.droidko.voicr.presenters.audioPost.receiver

import com.droidko.voicr.emvp.iEmvpPresenter
import com.droidko.voicr.models.AudioPost
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import org.jetbrains.anko.error

class AudioPostReceiverPrensenter(val output: iAudioPostReceiverOutput): iEmvpPresenter, iAudioPostReceiverInput {

    var subscriptionTimestamp = System.currentTimeMillis() / 1000L
    val channelChangeListener = object: ChildEventListener {
        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            // Do nothing...
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            // Do nothing...
        }

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val post = snapshot.getValue(AudioPost::class.java)
            output.onAudioPostReceived(post, post.timestamp >= subscriptionTimestamp)
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            // Do nothing...
        }

        override fun onCancelled(error: DatabaseError) {
            error { "An error occurred when listening for new audio posts: ${error.message}" }
        }

    }

    fun updateSubscriptionTimestamp() {
        subscriptionTimestamp = System.currentTimeMillis() / 1000L
    }

    override fun subsribeToChannel(channelId: String) {
        updateSubscriptionTimestamp()
        dbAccess().channelPosts(channelId).addChildEventListener(channelChangeListener)
    }

    override fun unsubscribeFromChannel(channelId: String) {
        dbAccess().channelPosts(channelId).removeEventListener(channelChangeListener)
    }

}