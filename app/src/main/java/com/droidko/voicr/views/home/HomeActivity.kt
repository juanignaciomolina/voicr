package com.droidko.voicr.views.home

import android.support.v4.app.Fragment
import com.droidko.voicr.views.BaseActivity

class HomeActivity: BaseActivity() {

    override fun onFragmentRequested(): Fragment {
        return HomeFragment()
    }

}