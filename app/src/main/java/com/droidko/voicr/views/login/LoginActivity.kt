package com.droidko.voicr.views.login

import android.support.v4.app.Fragment
import com.droidko.voicr.views.BaseActivity

class LoginActivity : BaseActivity() {

    override fun onFragmentRequested(): Fragment {
        return LoginFragment()
    }

}