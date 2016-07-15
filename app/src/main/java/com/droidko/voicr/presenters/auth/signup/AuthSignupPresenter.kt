package com.droidko.voicr.presenters.auth.signup

import com.droidko.voicr.emvp.iEmvpPresenter
import com.droidko.voicr.models.UserProfile
import com.droidko.voicr.presenters.auth.AuthPresenter
import com.droidko.voicr.presenters.auth.iAuthOutput
import com.droidko.voicr.presenters.editUserProfile.EditUserProfilePresenter
import com.droidko.voicr.presenters.editUserProfile.iEditUserProfileOutput
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import org.jetbrains.anko.error

class AuthSignupPresenter(val output: iAuthSignupOutput): iEmvpPresenter, iAuthSignupInput, iEditUserProfileOutput {

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val editUserProfilePresenter by lazy { EditUserProfilePresenter(this) }

    // ** SIGNUP **
    override fun doSignUp(email: String, password: String, confirmPassword: String) {

        if (validateSignUpInput(email, password, confirmPassword))
            requestFirebaseSignUp(email, password)
    }

    fun validateSignUpInput(email: String, password: String, confirmPassword: String): Boolean {
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            output.onSignUpFailure(iAuthOutput.AuthError.INVALID_EMAIL)
            return false
        }

        if(password.length < AuthPresenter.MIN_PASSWORD_LENGTH) {
            output.onSignUpFailure(iAuthOutput.AuthError.PASSWORD_TOO_SHORT)
            return false
        }

        if (!password.equals(confirmPassword)) {
            output.onSignUpFailure(iAuthOutput.AuthError.PASSWORDS_DONT_MATCH)
            return false
        }

        return true
    }

    fun requestFirebaseSignUp(email: String, password: String) {
        // Register a Firebase User
        firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    //After a Firebase user is registered, create an empty profile for it
                    val userProfile = UserProfile()
                    editUserProfilePresenter.newUserProfile(userProfile)
                }
                .addOnFailureListener { exception -> handleSignUpFirebaseError(exception) }
    }

    fun handleSignUpFirebaseError(exception: Exception) {
        when(exception) {
            is FirebaseAuthUserCollisionException -> output.onSignUpFailure(iAuthOutput.AuthError.EMAIL_TAKEN)
            else -> {
                error { "A firebase error occurred in $exception" }
                output.onSignUpFailure(iAuthOutput.AuthError.UNKNOWN)
            }
        }
    }

    override fun onUserProfileCreationSuccessful(userProfile: UserProfile) {
        output.onSignUpSuccessful(firebaseAuth.currentUser!!)
    }

    override fun onUserProfileCreationFailure(exception: Exception) {
        handleSignUpFirebaseError(exception)
    }
}