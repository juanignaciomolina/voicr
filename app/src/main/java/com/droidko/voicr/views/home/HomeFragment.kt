package com.droidko.voicr.views.home

import android.media.MediaPlayer
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import com.droidko.voicr.R
import com.droidko.voicr.extensions.loadUrl
import com.droidko.voicr.models.AudioPost
import com.droidko.voicr.presenters.audioPost.receiver.AudioPostReceiverPresenter
import com.droidko.voicr.presenters.audioPost.receiver.iAudioPostReceiverOutput
import com.droidko.voicr.presenters.audioPost.record.AudioPostPostRecordPresenter
import com.droidko.voicr.presenters.audioPost.record.iAudioPostRecordOutput
import com.droidko.voicr.views.BaseFragment
import com.droidko.voicr.views.newChannel.NewChannelActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.enabled
import org.jetbrains.anko.startActivity

class HomeFragment: BaseFragment(), iAudioPostRecordOutput, iAudioPostReceiverOutput {

    var mediaPlayer: MediaPlayer? = null

    val audioRecordPresenter by lazy { AudioPostPostRecordPresenter(this) }
    val audioReceiverPresenter by lazy { AudioPostReceiverPresenter(this) }

    //region Lifecycle
    override fun onLayoutRequested(): Int {
        return R.layout.fragment_home
    }

    override fun onPopulateUi(rootView: View) {
        vSendAudioButton.enabled = false
    }

    override fun onSetListeners(rootView: View) {
        vNewChannelButton.setOnClickListener {
            activity.startActivity<NewChannelActivity>()
        }

        vSendAudioButton.setOnTouchListener { view, motionEvent ->
            when(motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            audioRecordPresenter.channelId = vListeningChannelEditText.text.toString()
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

        vListenToChannelButton.setOnClickListener {
            if (!vListeningChannelEditText.text.isEmpty()) {
                vSendAudioButton.enabled = true
                audioReceiverPresenter.startListeningToChannel(vListeningChannelEditText.text.toString())
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Attention: For some reason, onResume() was being called multiple times, causing duplicated
        // listeners. This may be related to the library being used for runtime-permissions, since
        // it uses a separate Activity for handling results
        audioReceiverPresenter.startListeningToChannel("myChannel")         // TODO UNHARDCODE THIS
    }

    override fun onStop() {
        audioReceiverPresenter.stopListeningFromChannel("myChannel")
        super.onStop()
    }
    //endregion

    //region AudioPosts
    override fun onRecordFailure() {
        toast(R.string.general_error_unknown)
    }

    override fun onRecordSuccessful(pathToAudioRecord: String) {
        // Do something
    }

    override fun onAudioPostReceived(post: AudioPost, new: Boolean) {
        if (new) {
            vPlayingUserUsername.text = post.uid
            // TODO UNHARDCODE THIS
            vPlayingUserAvatar.loadUrl("https://lh5.googleusercontent.com/-QwLSi4cZPFw/AAAAAAAAAAI/AAAAAAAAwV8/B8TOXlKWf_Q/s0-c-k-no-ns/photo.jpg")
        }
    }
    //endregion
}