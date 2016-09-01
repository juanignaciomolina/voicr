package com.droidko.voicr.views.auth

import android.support.v4.app.Fragment
import com.droidko.voicr.views.BaseActivity

class AuthActivity : BaseActivity() {

    override fun onFragmentRequested(): Fragment {
        return AuthFragment.newInstance()
    }

}