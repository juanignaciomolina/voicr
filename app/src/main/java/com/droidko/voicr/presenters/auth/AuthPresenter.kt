package com.droidko.voicr.presenters.auth

import com.droidko.voicr.views.auth.iAuthView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import org.jetbrains.anko.error

class AuthPresenter(val authView: iAuthView) : iAuthPresenter {

    // Constants
    companion object {
        const val MIN_PASSWORD_LENGTH = 8
    }

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // ** LOGIN **
    override fun doLogin(email: String, password: String) {
        if (validateLoginInput(email, password))
            requestFirebaseLogin(email, password)
    }

    fun validateLoginInput(email: String, password: String): Boolean {
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            authView.onLoginFailure(iAuthView.AuthError.INVALID_EMAIL)
            return false
        }

        if(password.length < MIN_PASSWORD_LENGTH) {
            authView.onLoginFailure(iAuthView.AuthError.PASSWORD_TOO_SHORT)
            return false
        }

        return true
    }

    fun requestFirebaseLogin(email: String, password: String) {
        firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { authView.onLoginSuccessful(firebaseAuth.currentUser!!) }
                .addOnFailureListener { exception -> handleLoginFirebaseError(exception) }
    }

    fun handleLoginFirebaseError(exception: Exception) {
        when(exception) {
            is FirebaseAuthInvalidCredentialsException -> authView.onLoginFailure(iAuthView.AuthError.SERVER_REJECTED_CREDENTIALS)
            else -> {
                error { "A firebase error occurred in $exception" }
                authView.onLoginFailure(iAuthView.AuthError.UNKNOWN)
            }
        }
    }

    // ** SIGNUP **
    override fun doSignUp(email: String, password: String, confirmPassword: String) {

        if (validateSignUpInput(email, password, confirmPassword))
            requestFirebaseSignUp(email, password)
    }

    fun validateSignUpInput(email: String, password: String, confirmPassword: String): Boolean {
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            authView.onSignUpFailure(iAuthView.AuthError.INVALID_EMAIL)
            return false
        }

        if(password.length < MIN_PASSWORD_LENGTH) {
            authView.onSignUpFailure(iAuthView.AuthError.PASSWORD_TOO_SHORT)
            return false
        }

        if (!password.equals(confirmPassword)) {
            authView.onSignUpFailure(iAuthView.AuthError.PASSWORDS_DONT_MATCH)
            return false
        }

        return true
    }

    fun requestFirebaseSignUp(email: String, password: String) {
        firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authView.onSignUpSuccessful(firebaseAuth.currentUser!!) }
                .addOnFailureListener { exception -> handleSignUpFirebaseError(exception) }
    }

    fun handleSignUpFirebaseError(exception: Exception) {
        when(exception) {
            is FirebaseAuthUserCollisionException -> authView.onSignUpFailure(iAuthView.AuthError.EMAIL_TAKEN)
            else -> {
                error { "A firebase error occurred in $exception" }
                authView.onSignUpFailure(iAuthView.AuthError.UNKNOWN)
            }
        }
    }
}