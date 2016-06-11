package com.droidko.voicr.views.home

import android.media.MediaPlayer
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import com.droidko.voicr.R
import com.droidko.voicr.model.Message
import com.droidko.voicr.presenters.audio.AudioRecordPresenter
import com.droidko.voicr.presenters.audio.iAudioRecordView
import com.droidko.voicr.presenters.textMessages.TextMessagesPresenter
import com.droidko.voicr.views.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.error
import java.io.IOException

class HomeFragment: BaseFragment(), iHomeView, iAudioRecordView {

    var mediaPlayer: MediaPlayer? = null

    val textMessagesPresenter by lazy { TextMessagesPresenter(this) }
    val audioRecordPresenter by lazy { AudioRecordPresenter(this) }

    //region Lifecycle
    override fun onLayoutRequested(): Int {
        return R.layout.fragment_home
    }

    override fun onPopulateUi(rootView: View) {
        vSendMessageButton.setOnClickListener {
            textMessagesPresenter.sendMessage(vNewMessageEditText.text.toString())

            vNewMessageEditText.text.clear()
            toast("Sending message...")
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

    override fun onResume() {
        super.onResume()
        textMessagesPresenter.startListeningForMessages()
    }

    override fun onPause() {
        textMessagesPresenter.stopListeningForMessages()
        super.onPause()
    }
    //endregion

    //region Text messages
    override fun onMessageArrive(message: Message) {
        vMessageViewer.text = message.message
        vUsernameViewer.text = message.userEmail
    }
    //endregion

    //region Audio recording
    override fun onRecordFailure() {
        toast(R.string.general_error_unknown)
    }

    override fun onRecordSuccessful(pathToAudioRecord: String) {
        toast("Playing audio")
        playAudio(pathToAudioRecord)
    }

    fun playAudio(pathToAudioRecord: String) {
        mediaPlayer = MediaPlayer()
        try {
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
    //endregion
}