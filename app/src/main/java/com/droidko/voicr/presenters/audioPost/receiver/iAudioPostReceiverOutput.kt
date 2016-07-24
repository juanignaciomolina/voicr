package com.droidko.voicr.presenters.audioPost.receiver

import com.droidko.voicr.emvp.iEmvpOutput
import com.droidko.voicr.models.AudioPost

interface iAudioPostReceiverOutput: iEmvpOutput {


    /**
     * Callback triggered whenever an AudioPost is received from an
     * audio channel that is being actively listened to
     * @param post: An instance of an [AudioPost] received
     * @param new: A [Boolean] with a 'true' value if the [post] has been created
     *              after being subscribed to the channel or 'false' if it was created
     *              previously
     */
    fun onAudioPostReceived(post: AudioPost, new: Boolean)

}