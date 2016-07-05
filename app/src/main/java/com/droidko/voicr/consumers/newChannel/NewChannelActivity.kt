package com.droidko.voicr.consumers.newChannel

import android.support.v4.app.Fragment
import com.droidko.voicr.consumers.BaseActivity

class NewChannelActivity: BaseActivity() {

    override fun onFragmentRequested(): Fragment {
        return NewChannelFragment()
    }

}
