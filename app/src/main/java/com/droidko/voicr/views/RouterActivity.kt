package com.droidko.voicr.views

import android.app.Activity
import android.os.Bundle
import com.droidko.voicr.views.auth.AuthActivity
import com.droidko.voicr.views.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.startActivity

/* *
* RouterActivity is a routing activity that acts as an entry point for the app.
*
* The main responsibility of this activity is to redirect the userProfile to the HomeActivity or
* to the AuthActivity depending on weather there is a a logged userProfile stored on the app or not.
*
* */

class RouterActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (FirebaseAuth.getInstance().currentUser != null)
            startActivity<MainActivity>()
        else
            startActivity<AuthActivity>()
        finish()
    }
}
