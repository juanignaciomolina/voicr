package com.droidko.voicr.producers.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import org.jetbrains.anko.error

class AuthProducer(val authOutput: iAuthOutput): iAuthInput {

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
            authOutput.onLoginFailure(iAuthOutput.AuthError.INVALID_EMAIL)
            return false
        }

        if (password.length < MIN_PASSWORD_LENGTH) {
            authOutput.onLoginFailure(iAuthOutput.AuthError.PASSWORD_TOO_SHORT)
            return false
        }

        return true
    }

    fun requestFirebaseLogin(email: String, password: String) {
        firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { authOutput.onLoginSuccessful(firebaseAuth.currentUser!!) }
                .addOnFailureListener { exception -> handleLoginFirebaseError(exception) }
    }

    fun handleLoginFirebaseError(exception: Exception) {
        when(exception) {
            is FirebaseAuthInvalidCredentialsException -> authOutput.onLoginFailure(iAuthOutput.AuthError.SERVER_REJECTED_CREDENTIALS)
            is FirebaseAuthInvalidUserException -> authOutput.onLoginFailure(iAuthOutput.AuthError.EMAIL_NOT_REGISTERED)
            else -> {
                error { "A firebase error occurred in $exception" }
                authOutput.onLoginFailure(iAuthOutput.AuthError.UNKNOWN)
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
            authOutput.onSignUpFailure(iAuthOutput.AuthError.INVALID_EMAIL)
            return false
        }

        if(password.length < MIN_PASSWORD_LENGTH) {
            authOutput.onSignUpFailure(iAuthOutput.AuthError.PASSWORD_TOO_SHORT)
            return false
        }

        if (!password.equals(confirmPassword)) {
            authOutput.onSignUpFailure(iAuthOutput.AuthError.PASSWORDS_DONT_MATCH)
            return false
        }

        return true
    }

    fun requestFirebaseSignUp(email: String, password: String) {
        firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authOutput.onSignUpSuccessful(firebaseAuth.currentUser!!) }
                .addOnFailureListener { exception -> handleSignUpFirebaseError(exception) }
    }

    fun handleSignUpFirebaseError(exception: Exception) {
        when(exception) {
            is FirebaseAuthUserCollisionException -> authOutput.onSignUpFailure(iAuthOutput.AuthError.EMAIL_TAKEN)
            else -> {
                error { "A firebase error occurred in $exception" }
                authOutput.onSignUpFailure(iAuthOutput.AuthError.UNKNOWN)
            }
        }
    }
}