package com.droidko.voicr.views.home

import android.support.v4.app.Fragment
import com.droidko.voicr.views.BaseActivity
import com.droidko.voicr.views.channel.ChannelFragment

class ChannelActivity: BaseActivity() {

    override fun onFragmentRequested(): Fragment {
        return ChannelFragment.newInstance(intent.extras.getString(ChannelFragment.EXTRA_CHANNEL_ID))
    }

}