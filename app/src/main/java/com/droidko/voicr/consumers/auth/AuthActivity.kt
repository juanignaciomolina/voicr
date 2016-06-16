package com.droidko.voicr.consumers.auth

import android.support.v4.app.Fragment
import com.droidko.voicr.consumers.BaseActivity

class AuthActivity : BaseActivity() {

    override fun onFragmentRequested(): Fragment {
        return AuthFragment()
    }

}