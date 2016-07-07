package com.droidko.voicr.views.home

import android.media.AudioManager
import android.media.MediaPlayer
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import com.bumptech.glide.Glide
import com.droidko.voicr.R
import com.droidko.voicr.models.AudioPost
import com.droidko.voicr.presenters.audioPost.receiver.AudioPostReceiverPrensenter
import com.droidko.voicr.presenters.audioPost.receiver.iAudioPostReceiverOutput
import com.droidko.voicr.presenters.audioPost.record.AudioPostPostRecordPresenter
import com.droidko.voicr.presenters.audioPost.record.iAudioPostRecordOutput
import com.droidko.voicr.views.BaseFragment
import com.droidko.voicr.views.newChannel.NewChannelActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.error
import org.jetbrains.anko.startActivity
import java.io.IOException

class HomeFragment: BaseFragment(), iAudioPostRecordOutput, iAudioPostReceiverOutput {

    var mediaPlayer: MediaPlayer? = null

    val audioRecordPresenter by lazy { AudioPostPostRecordPresenter(this) }
    val audioReceiverPresenter by lazy { AudioPostReceiverPrensenter(this) }

    //region Lifecycle
    override fun onLayoutRequested(): Int {
        return R.layout.fragment_home
    }

    override fun onSetListeners(rootView: View) {
        vNewChannelButton.setOnClickListener {
            activity.startActivity<NewChannelActivity>()
        }

        vSendAudioButton.setOnTouchListener { view, motionEvent ->
            when(motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            audioRecordPresenter.startRecording()
                            (view as Button).text = "Recording"
                            return@setOnTouchListener true
                        }
                        MotionEvent.ACTION_UP -> {
                            audioRecordPresenter.stopRecording()
                            (view as Button).text = "Audio"
                            return@setOnTouchListener true
                        }
                        else -> { return@setOnTouchListener false}
                    }
        }
    }

    override fun onStart() {
        super.onStart()
        // Attention: For some reason, onResume() was being called multiple times, causing duplicated
        // listeners. This may be related to the library being used for runtime-permissions, since
        // it uses a separate Activity for handling results
        audioReceiverPresenter.subsribeToChannel("myChannel")         // TODO UNHARDCODE THIS
    }

    override fun onStop() {
        audioReceiverPresenter.unsubscribeFromChannel("myChannel")
        super.onStop()
    }
    //endregion

    //region AudioPosts
    override fun onRecordFailure() {
        toast(R.string.general_error_unknown)
    }

    override fun onRecordSuccessful(pathToAudioRecord: String) {
        //toast("Playing audio")
        //playAudio(pathToAudioRecord)
    }

    fun playAudio(pathToAudioRecord: String) {
        mediaPlayer = MediaPlayer()
        try {
            mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer?.setDataSource(pathToAudioRecord);
            mediaPlayer?.prepare();
            mediaPlayer?.start();
        } catch (e: IOException) {
            error { "MediaPlayer failed on method prepare(). Exception: ${e.message}" }
            freeMediaPlayer()
        }
    }

    fun stopAudio() {
        try {
            mediaPlayer?.stop()
        } catch(e: Exception) {
            error { "MediaPlayer failed on method stop(). Exception: ${e.message}" }
        } finally {
            freeMediaPlayer()
        }
    }

    fun freeMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onAudioPostReceived(post: AudioPost, new: Boolean) {
        if (new) {
            vPlayingUserUsername.text = post.uid
            // TODO UNHARDCODE THIS
            Glide
                    .with(this)
                    .load("https://lh5.googleusercontent.com/-QwLSi4cZPFw/AAAAAAAAAAI/AAAAAAAAwV8/B8TOXlKWf_Q/s0-c-k-no-ns/photo.jpg")
                    .into(vPlayingUserAvatar)
            playAudio(post.downloadUrl)
        }
    }
    //endregion
}