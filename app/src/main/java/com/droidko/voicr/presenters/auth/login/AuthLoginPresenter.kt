package com.droidko.voicr.presenters.auth.login

import com.droidko.voicr.emvp.iEmvpPresenter
import com.droidko.voicr.presenters.auth.AuthPresenter
import com.droidko.voicr.presenters.auth.iAuthOutput
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import org.jetbrains.anko.error

class AuthLoginPresenter(val output: iAuthLoginOutput): iEmvpPresenter, iAuthLoginInput {

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // ** LOGIN **
    override fun doLogin(email: String, password: String) {
        if (validateLoginInput(email, password))
            requestFirebaseLogin(email, password)
    }

    fun validateLoginInput(email: String, password: String): Boolean {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            output.onLoginFailure(iAuthOutput.AuthError.INVALID_EMAIL)
            return false
        }

        if (password.length < AuthPresenter.MIN_PASSWORD_LENGTH) {
            output.onLoginFailure(iAuthOutput.AuthError.PASSWORD_TOO_SHORT)
            return false
        }

        return true
    }

    fun requestFirebaseLogin(email: String, password: String) {
        firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { output.onLoginSuccessful(firebaseAuth.currentUser!!) }
                .addOnFailureListener { exception -> handleLoginFirebaseError(exception) }
    }

    fun handleLoginFirebaseError(exception: Exception) {
        when(exception) {
            is FirebaseAuthInvalidCredentialsException -> output.onLoginFailure(iAuthOutput.AuthError.SERVER_REJECTED_CREDENTIALS)
            is FirebaseAuthInvalidUserException -> output.onLoginFailure(iAuthOutput.AuthError.EMAIL_NOT_REGISTERED)
            else -> {
                error { "A firebase error occurred in $exception" }
                output.onLoginFailure(iAuthOutput.AuthError.UNKNOWN)
            }
        }
    }

}