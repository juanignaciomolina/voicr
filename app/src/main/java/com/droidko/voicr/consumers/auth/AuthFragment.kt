package com.droidko.voicr.consumers.auth

import android.view.View
import com.droidko.voicr.R
import com.droidko.voicr.producers.auth.AuthProducer
import com.droidko.voicr.consumers.BaseFragment
import com.droidko.voicr.consumers.home.HomeActivity
import com.droidko.voicr.producers.auth.iAuthOutput
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.startActivity

class AuthFragment : BaseFragment(), iAuthOutput {

    val presenter by lazy { AuthProducer(this) }

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

    override fun onSignUpFailure(error: iAuthOutput.AuthError) {
        handleError(error)
    }

    override fun onLoginSuccessful(user: FirebaseUser) {
        greetUser(user.email as String)
    }

    override fun onLoginFailure(error: iAuthOutput.AuthError) {
        handleError(error)
    }

    fun greetUser(email: String) {
        toast("Welcome $email")
        context.startActivity<HomeActivity>()
    }

    fun handleError(error: iAuthOutput.AuthError) {
        // Clear views
        vEmailEditText.error = null
        vPasswordEditText.error = null
        vConfirmPasswordEditText.error = null

        // Show appropriate error message
        when(error) {
            iAuthOutput.AuthError.SERVER_REJECTED_CREDENTIALS -> toast(R.string.auth_error_invalid_credentials)
            iAuthOutput.AuthError.INVALID_EMAIL -> vEmailEditText.error = getString(R.string.auth_error_invalid_email)
            iAuthOutput.AuthError.PASSWORD_TOO_SHORT -> vPasswordEditText.error = getString(R.string.auth_error_invalid_password)
            iAuthOutput.AuthError.PASSWORDS_DONT_MATCH -> vConfirmPasswordEditText.error = getString(R.string.auth_error_passwords_dont_match)
            iAuthOutput.AuthError.NETWORK -> toast(R.string.general_error_network)
            iAuthOutput.AuthError.UNKNOWN -> toast(R.string.general_error_unknown)
            iAuthOutput.AuthError.EMAIL_TAKEN -> toast(R.string.auth_error_email_taken)
            iAuthOutput.AuthError.EMAIL_NOT_REGISTERED -> toast(R.string.auth_error_email_not_registered)
        }
    }

}
