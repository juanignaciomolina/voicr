package com.droidko.voicr.views.newChannel

import android.view.View
import com.droidko.voicr.R
import com.droidko.voicr.views.BaseFragment
import kotlinx.android.synthetic.main.fragment_new_channel.*
import org.jetbrains.anko.toast

class NewChannelFragment: BaseFragment() {

    val presenter by lazy { NewChannelPresenter(this) }

    override fun onLayoutRequested(): Int {
        return R.layout.fragment_new_channel
    }

    override fun onSetListeners(rootView: View) {
        vCreateChannelButton.setOnClickListener {
            if (!vNewChannelName.text.isEmpty())
                presenter.createChannel(vNewChannelName.text.toString())
        }
    }

    override fun onChannelCreationSuccessful(channelId: String, channelName: String) {
        activity.toast("Channel created")
    }

    override fun onChannelCreationFailure(exception: Exception) {
        activity.toast("An error occurred while creating the channel")
    }

}