package com.droidko.voicr.views.channel

import android.animation.Animator
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MotionEvent
import android.view.View
import android.view.ViewAnimationUtils
import com.droidko.voicr.R
import com.droidko.voicr.models.AudioPost
import com.droidko.voicr.presenters.audioPost.receiver.AudioPostReceiverPresenter
import com.droidko.voicr.presenters.audioPost.receiver.iAudioPostReceiverOutput
import com.droidko.voicr.presenters.audioPost.record.AudioPostPostRecordPresenter
import com.droidko.voicr.presenters.audioPost.record.iAudioPostRecordOutput
import com.droidko.voicr.views.BaseFragment
import com.droidko.voicr.views.channel.recycler.ChannelRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_channel.*
import org.jetbrains.anko.error
import org.jetbrains.anko.info

class ChannelFragment: BaseFragment(), iAudioPostRecordOutput, iAudioPostReceiverOutput {

    companion object {
        val EXTRA_CHANNEL_ID = "extraChannelId"
        private val ANIM_CIRCULAR_REVEAL_IN_DURATION = 200L
        private val ANIM_CIRCULAR_REVEAL_OUT_DURATION = 400L

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
    val recyclerAdapter = ChannelRecyclerAdapter()

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

        with (vMessagesRecycler) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = recyclerAdapter
        }
    }

    override fun onPopulateUi(rootView: View) {
        vChannelTitle.text = audioChannel
        vRecordingMicReveal.visibility = View.INVISIBLE
    }

    override fun onSetListeners(rootView: View) {

        vSendAudioButton.setOnTouchListener { view, motionEvent ->
            when(motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            audioRecordPresenter.startRecording()
                            animateCircularRevealIn()
                            return@setOnTouchListener true
                        }
                        MotionEvent.ACTION_UP -> {
                            audioRecordPresenter.stopRecording()
                            animateCircularRevealOut()
                            return@setOnTouchListener true
                        }
                        else -> { return@setOnTouchListener false}
                    }
        }

        recyclerAdapter.clickListener = object : ChannelRecyclerAdapter.ClickListener {
            override fun onPlayClicked(post: AudioPost) {
                info { "Playing audio of user ${post.uid}" }
                audioReceiverPresenter.playAudio(post.downloadUrl)
            }

            override fun onAvatarClicked(post: AudioPost) {
                info { "Avatar clicked of user ${post.uid}" }
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
        showAudioPost(post, new)
    }

    fun showAudioPost(post: AudioPost, new: Boolean) {
        if (vDotLoadingIndicator.visibility == View.VISIBLE) {
            vDotLoadingIndicator.visibility = View.GONE
            vMessagesRecycler.visibility = View.VISIBLE
        }
        recyclerAdapter.addAudioPost(post)
        vMessagesRecycler.scrollToPosition(0)
    }
    //endregion

    //region animations
    fun animateCircularRevealIn() {

        vRecordingMicReveal.visibility = View.VISIBLE

        // get the center for the clipping circle, in this case the FAB button
        val centerX = (vSendAudioButton.left + vSendAudioButton.right) / 2
        val centerY = (vSendAudioButton.top + vSendAudioButton.bottom) / 2

        val startRadius = 0f
        // get the final radius for the clipping circle
        val endRadius = Math
                .hypot(vRecordingMicReveal.width.toDouble(), vRecordingMicReveal.height.toDouble())
                .toFloat()

        // create the animator for this view (the start radius is zero)
        val circularRevealAnimation = ViewAnimationUtils.createCircularReveal(
                vRecordingMicReveal,
                centerX,
                centerY,
                startRadius,
                endRadius)

        circularRevealAnimation.duration = ANIM_CIRCULAR_REVEAL_IN_DURATION

        circularRevealAnimation.start()
        vRecordingMicReveal.startCounter()
    }

    fun animateCircularRevealOut() {

        vRecordingMicReveal.visibility = View.VISIBLE

        // get the center for the clipping circle, in this case the FAB button
        val centerX = (vSendAudioButton.left + vSendAudioButton.right) / 2
        val centerY = (vSendAudioButton.top + vSendAudioButton.bottom) / 2

        val endRadius = 0f
        // get the start radius for the clipping circle
        val startRadius = Math
                .hypot(vRecordingMicReveal.width.toDouble(), vRecordingMicReveal.height.toDouble())
                .toFloat()

        // create the animator for this view (the start radius is zero)
        val circularRevealAnimation = ViewAnimationUtils.createCircularReveal(
                vRecordingMicReveal,
                centerX,
                centerY,
                startRadius,
                endRadius)

        circularRevealAnimation.duration = ANIM_CIRCULAR_REVEAL_OUT_DURATION

        circularRevealAnimation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                vRecordingMicReveal.visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }

        })

        circularRevealAnimation.start()
        vRecordingMicReveal.stopCounter()
    }
    //endregion
}