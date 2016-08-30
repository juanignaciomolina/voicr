package com.droidko.voicr.views.newChannel

import android.support.v4.app.Fragment
import com.droidko.voicr.views.BaseActivity

class NewChannelActivity: BaseActivity() {

    override fun onFragmentRequested(): Fragment {
        return NewChannelFragment.newInstance()
    }

}
