package com.droidko.voicr.presenters.audioPost.receiver

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.droidko.voicr.VoicrApplication
import com.droidko.voicr.emvp.iEmvpPresenter

class AudioPostReceiverPresenter(val output: iAudioPostReceiverOutput): iEmvpPresenter, iAudioPostReceiverInput {

    var listeningChannel: String? = null
    var receiverService: AudioPostReceiverService? = null
    var service: AudioPostReceiverService? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            // We've bound to AudioPostReceiverService, cast the IBinder and get AudioPostReceiverService instance
            val localBinder = binder as AudioPostReceiverService.AudioPostReceiverBinder
            service = localBinder.service

            startListeningToChannel(listeningChannel!!) //If the service was started, there must be a channel to listen to
            service!!.setPresenterOutput(output)
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            service = null
        }
    }

    override fun startListeningToChannel(channelId: String) {
        listeningChannel = channelId
        if (service == null) {
            boundReceiverService()
        } else {
            service!!.startListeningToChannel(channelId)
        }
    }

    override fun stopListeningFromChannel(channelId: String) {
        if (service != null) service!!.stopListeningFromChannel(channelId)
    }

    fun boundReceiverService() {
        // Start an AudioPostReceiverService with bounding
        val intent = Intent(VoicrApplication.Companion.instance, AudioPostReceiverService::class.java)
        VoicrApplication.instance.bindService(intent, serviceConnection,Context.BIND_AUTO_CREATE)
    }

    fun playAudio(pathToAudioRecord: String) {
        service?.playAudio(pathToAudioRecord)
    }
}