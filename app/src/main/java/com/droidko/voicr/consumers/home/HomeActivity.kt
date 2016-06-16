package com.droidko.voicr.consumers.home

import android.support.v4.app.Fragment
import com.droidko.voicr.consumers.BaseActivity

class HomeActivity: BaseActivity() {

    override fun onFragmentRequested(): Fragment {
        return HomeFragment()
    }

}