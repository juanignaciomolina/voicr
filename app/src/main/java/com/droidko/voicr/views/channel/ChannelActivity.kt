package com.droidko.voicr.views.main.home

import android.support.v4.app.Fragment
import com.droidko.voicr.views.BaseActivity
import com.droidko.voicr.views.channel.ChannelFragment

class ChannelActivity: BaseActivity() {

    override fun onFragmentRequested(): Fragment {
        return ChannelFragment.newInstance(intent.extras.getSerializable(ChannelFragment.EXTRA_CHANNEL_PROFILE))
    }

}