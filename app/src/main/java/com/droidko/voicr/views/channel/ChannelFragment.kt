package com.droidko.voicr.views.home

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import com.droidko.voicr.R
import com.droidko.voicr.models.AudioPost
import com.droidko.voicr.presenters.audioPost.receiver.AudioPostReceiverPresenter
import com.droidko.voicr.presenters.audioPost.receiver.iAudioPostReceiverOutput
import com.droidko.voicr.presenters.audioPost.record.AudioPostPostRecordPresenter
import com.droidko.voicr.presenters.audioPost.record.iAudioPostRecordOutput
import com.droidko.voicr.views.BaseFragment
import kotlinx.android.synthetic.main.fragment_channel.*
import org.jetbrains.anko.error

class ChannelFragment: BaseFragment(), iAudioPostRecordOutput, iAudioPostReceiverOutput {

    companion object {
        val EXTRA_CHANNEL_ID = "extraChannelId"

        fun newInstance(channelId: String): ChannelFragment {
            val f = ChannelFragment()
            val args = Bundle()
            args.putString(EXTRA_CHANNEL_ID, channelId)
            f.arguments = args
            return f
        }
    }

    val audioRecordPresenter by lazy { AudioPostPostRecordPresenter(this) }
    val audioReceiverPresenter by lazy { AudioPostReceiverPresenter(this) }

    var audioChannel = ""

    //region Lifecycle
    override fun onLayoutRequested(): Int {
        return R.layout.fragment_channel
    }

    override fun handleArguments(arguments: Bundle): Boolean {
        if (arguments.containsKey(EXTRA_CHANNEL_ID)) {
            audioChannel = arguments.getString(EXTRA_CHANNEL_ID)
            return true
        } else {
            error { "A channel id is necessary. Remember to provide one as an intent extra with " +
                    "EXTRA_CHANNEL_ID as a key" }
            return false
        }
    }


    override fun onInitialize(rootView: View) {
        audioRecordPresenter.channelId = audioChannel
    }

    override fun onPopulateUi(rootView: View) {
        vChannelTitle.text = audioChannel
    }

    override fun onSetListeners(rootView: View) {

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
        audioReceiverPresenter.startListeningToChannel(audioChannel)
    }

    override fun onStop() {
        audioReceiverPresenter.stopListeningFromChannel(audioChannel)
        super.onStop()
    }
    //endregion

    //region AudioPosts
    override fun onRecordFailure() {
        toast(getString(R.string.general_error_unknown))
    }

    override fun onRecordSuccessful(pathToAudioRecord: String) {
        // Do something
    }

    override fun onAudioPostReceived(post: AudioPost, new: Boolean) {
        // Do nothing
    }
    //endregion
}