package com.droidko.voicr.presenters.audioPost.receiver

import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.droidko.voicr.firebase.DbAccess
import com.droidko.voicr.models.AudioPost
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import org.jetbrains.anko.doAsync
import java.io.IOException


class AudioPostReceiverService: Service() {

    companion object {
        val TAG = "ReceiverService"
    }

    val binder = AudioPostReceiverBinder()
    var mediaPlayer: MediaPlayer? = null
    var output: iAudioPostReceiverOutput? = null
    var channelBeingReceived: String? = null

    //region Service lifecycle
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return Service.START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        // Use binding to communicate with presenters
        return binder
    }

    override fun onDestroy() {
        clearActiveListenings()
        super.onDestroy()
    }

    //endregion

    //region Database listening
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
            val isNew = post.timestamp >= subscriptionTimestamp

            if (isNew) playAudio(post.downloadUrl)
            output?.onAudioPostReceived(post, isNew)
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

    fun startListeningToChannel(channelId: String) {
        updateSubscriptionTimestamp()
        // If we were listening to a channel, stop receiving updates from it first
        clearActiveListenings()
        channelBeingReceived = channelId
        DbAccess().channelPosts(channelId).addChildEventListener(channelChangeListener)
    }

    fun stopListeningFromChannel(channelId: String) {
        DbAccess().channelPosts(channelId).removeEventListener(channelChangeListener)
    }

    fun clearActiveListenings() {
        // If we were listening to a channel, stop receiving updates from it first
        if (channelBeingReceived != null) {
            stopListeningFromChannel(channelBeingReceived!!)
        }
    }
    //endregion

    //region Playing audios
    fun playAudio(pathToAudioRecord: String) {
        doAsync {
            mediaPlayer = MediaPlayer()
            try {
                mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
                mediaPlayer?.setDataSource(pathToAudioRecord);
                mediaPlayer?.prepare();
                mediaPlayer?.start();
            } catch (e: IOException) {
                freeMediaPlayer()
                error { "MediaPlayer failed on method prepare(). Exception: ${e.message}" }
            }
        }
    }

    fun stopAudio() {
        doAsync {
            try {
                mediaPlayer?.stop()
            } catch(e: Exception) {
                error { "MediaPlayer failed on method stop(). Exception: ${e.message}" }
            } finally {
                freeMediaPlayer()
            }
        }
    }

    fun freeMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
    //endregion

    //region IBinder
    inner class AudioPostReceiverBinder : Binder() {
        val service: AudioPostReceiverService = this@AudioPostReceiverService
    }
    //endregion

    //region Presenter output
    fun setPresenterOutput(output: iAudioPostReceiverOutput) {
        this.output = output
    }
    //
}