package com.droidko.voicr.views.main

import android.support.v4.app.Fragment
import com.droidko.voicr.views.BaseActivity

class MainActivity : BaseActivity() {

    override fun onFragmentRequested(): Fragment {
        return MainFragment.newInstance()
    }

}