package com.droidko.voicr.producers.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import org.jetbrains.anko.error

class AuthProducer(val authView: iAuthOutput) : iAuthInput {

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
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            authView.onLoginFailure(iAuthOutput.AuthError.INVALID_EMAIL)
            return false
        }

        if (password.length < MIN_PASSWORD_LENGTH) {
            authView.onLoginFailure(iAuthOutput.AuthError.PASSWORD_TOO_SHORT)
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
            is FirebaseAuthInvalidCredentialsException -> authView.onLoginFailure(iAuthOutput.AuthError.SERVER_REJECTED_CREDENTIALS)
            is FirebaseAuthInvalidUserException -> authView.onLoginFailure(iAuthOutput.AuthError.EMAIL_NOT_REGISTERED)
            else -> {
                error { "A firebase error occurred in $exception" }
                authView.onLoginFailure(iAuthOutput.AuthError.UNKNOWN)
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
            authView.onSignUpFailure(iAuthOutput.AuthError.INVALID_EMAIL)
            return false
        }

        if(password.length < MIN_PASSWORD_LENGTH) {
            authView.onSignUpFailure(iAuthOutput.AuthError.PASSWORD_TOO_SHORT)
            return false
        }

        if (!password.equals(confirmPassword)) {
            authView.onSignUpFailure(iAuthOutput.AuthError.PASSWORDS_DONT_MATCH)
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
            is FirebaseAuthUserCollisionException -> authView.onSignUpFailure(iAuthOutput.AuthError.EMAIL_TAKEN)
            else -> {
                error { "A firebase error occurred in $exception" }
                authView.onSignUpFailure(iAuthOutput.AuthError.UNKNOWN)
            }
        }
    }
}