package com.droidko.voicr.views

import android.app.Activity
import android.os.Bundle
import com.droidko.voicr.views.auth.AuthActivity
import com.droidko.voicr.views.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.startActivity

/* *
* RouterActivity is a routing activity that acts as an entry point for the app.
*
* The main responsibility of this activity is to redirect the user to the HomeActivity or
* to the AuthActivity depending on weather there is a a logged user stored on the app or not.
*
* */

class RouterActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (FirebaseAuth.getInstance().currentUser != null)
            startActivity<HomeActivity>()
        else
            startActivity<AuthActivity>()
        finish()
    }
}
