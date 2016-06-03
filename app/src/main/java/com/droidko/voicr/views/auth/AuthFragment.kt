package com.droidko.voicr.views.auth

import android.view.View
import com.droidko.voicr.R
import com.droidko.voicr.presenters.auth.AuthPresenter
import com.droidko.voicr.views.BaseFragment
import com.droidko.voicr.views.home.HomeActivity
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.startActivity

class AuthFragment : BaseFragment(), iAuthView {

    val presenter by lazy { AuthPresenter(this) }

    override fun onLayoutRequested(): Int {
        return R.layout.fragment_login
    }

    override fun onPopulateUi(rootView: View) {
        vLoginButton.setOnClickListener() {
            presenter.doLogin(
                    vEmailEditText.text.toString(),
                    vPasswordEditText.text.toString())
        }

        vSignUpButton.setOnClickListener() {
            presenter.doSignUp(
                    vEmailEditText.text.toString(),
                    vPasswordEditText.text.toString(),
                    vConfirmPasswordEditText.text.toString())
        }
    }

    override fun onSignUpSuccessful(user: FirebaseUser) {
        greetUser(user.email as String)
    }

    override fun onSignUpFailure(error: iAuthView.AuthError) {
        handleError(error)
    }

    override fun onLoginSuccessful(user: FirebaseUser) {
        greetUser(user.email as String)
    }

    override fun onLoginFailure(error: iAuthView.AuthError) {
        handleError(error)
    }

    fun greetUser(email: String) {
        toast("Welcome $email")
        context.startActivity<HomeActivity>()
    }

    fun handleError(error: iAuthView.AuthError) {
        // Clear views
        vEmailEditText.error = null
        vPasswordEditText.error = null
        vConfirmPasswordEditText.error = null

        // Show appropriate error message
        when(error) {
            iAuthView.AuthError.SERVER_REJECTED_CREDENTIALS -> toast(R.string.auth_error_invalid_credentials)
            iAuthView.AuthError.INVALID_EMAIL -> vEmailEditText.error = getString(R.string.auth_error_invalid_email)
            iAuthView.AuthError.PASSWORD_TOO_SHORT -> vPasswordEditText.error = getString(R.string.auth_error_invalid_password)
            iAuthView.AuthError.PASSWORDS_DONT_MATCH -> vConfirmPasswordEditText.error = getString(R.string.auth_error_passwords_dont_match)
            iAuthView.AuthError.NETWORK -> toast(R.string.general_error_network)
            iAuthView.AuthError.UNKNOWN -> toast(R.string.general_error_unknown)
            iAuthView.AuthError.EMAIL_TAKEN -> toast(R.string.auth_error_email_taken)
            iAuthView.AuthError.EMAIL_NOT_REGISTERED -> toast(R.string.auth_error_email_not_registered)
        }
    }

}
