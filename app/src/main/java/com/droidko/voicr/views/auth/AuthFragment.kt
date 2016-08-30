package com.droidko.voicr.views.auth

import android.view.View
import com.droidko.voicr.R
import com.droidko.voicr.presenters.auth.iAuthOutput
import com.droidko.voicr.presenters.auth.login.AuthLoginPresenter
import com.droidko.voicr.presenters.auth.login.iAuthLoginOutput
import com.droidko.voicr.presenters.auth.signup.AuthSignupPresenter
import com.droidko.voicr.presenters.auth.signup.iAuthSignupOutput
import com.droidko.voicr.views.BaseFragment
import com.droidko.voicr.views.home.HomeActivity
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.startActivity

class AuthFragment : BaseFragment(), iAuthLoginOutput, iAuthSignupOutput {

    val loginPresenter by lazy { AuthLoginPresenter(this) }
    val signupPresenter by lazy { AuthSignupPresenter(this) }

    override fun onLayoutRequested(): Int {
        return R.layout.fragment_login
    }

    override fun onSetListeners(rootView: View) {
        vLoginButton.setOnClickListener() {
            loginPresenter.doLogin(
                    vEmailEditText.text.toString(),
                    vPasswordEditText.text.toString())
        }

        vSignUpButton.setOnClickListener() {
            signupPresenter.doSignUp(
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
            iAuthOutput.AuthError.SERVER_REJECTED_CREDENTIALS -> toast(getString(R.string.auth_error_invalid_credentials))
            iAuthOutput.AuthError.INVALID_EMAIL -> vEmailEditText.error = getString(R.string.auth_error_invalid_email)
            iAuthOutput.AuthError.PASSWORD_TOO_SHORT -> vPasswordEditText.error = getString(R.string.auth_error_invalid_password)
            iAuthOutput.AuthError.PASSWORDS_DONT_MATCH -> vConfirmPasswordEditText.error = getString(R.string.auth_error_passwords_dont_match)
            iAuthOutput.AuthError.NETWORK -> toast(getString(R.string.general_error_network))
            iAuthOutput.AuthError.UNKNOWN -> toast(getString(R.string.general_error_unknown))
            iAuthOutput.AuthError.EMAIL_TAKEN -> toast(getString(R.string.auth_error_email_taken))
            iAuthOutput.AuthError.EMAIL_NOT_REGISTERED -> toast(getString(R.string.auth_error_email_not_registered))
        }
    }

}
