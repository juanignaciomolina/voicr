package com.droidko.voicr.presenters.login

import com.droidko.voicr.views.login.iLoginView
import org.jetbrains.anko.info

class LoginPresenter(val loginView: iLoginView) : iLoginPresenter {

    override fun doLogin(email: String, password: String, confirmPassword: String) {
        // Test code
        info("Email: $email Password: $password ConfirmPassword: $confirmPassword")
        loginView.onLoginSuccessful()
    }

}