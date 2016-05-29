package com.droidko.voicr.views.login

import android.view.View
import com.droidko.voicr.R
import com.droidko.voicr.presenters.login.LoginPresenter
import com.droidko.voicr.views.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment(), iLoginView {

    val presenter by lazy { LoginPresenter(this) }

    override fun onLayoutRequested(): Int {
        return R.layout.fragment_login
    }

    override fun onPopulateUi(rootView: View) {
        vStartButton.setOnClickListener() {
            presenter.doLogin(
                    vEmailEditText.text.toString(),
                    vPasswordEditText.text.toString(),
                    vConfirmPasswordEditText.text.toString())
        }
    }

    override fun onLoginSuccessful() {
        toast("Login successful")
    }

    override fun onLoginFailure() {
        toast("Login failure")
    }

}
