package com.droidko.voicr.presenters.auth.signup

import com.droidko.voicr.emvp.iEmvpPresenter
import com.droidko.voicr.models.user.UserProfile
import com.droidko.voicr.models.user.UserSubs
import com.droidko.voicr.presenters.auth.AuthPresenter
import com.droidko.voicr.presenters.auth.iAuthOutput
import com.droidko.voicr.presenters.user.editProfile.EditUserProfilePresenter
import com.droidko.voicr.presenters.user.editProfile.iEditUserProfileOutput
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import org.jetbrains.anko.error

class AuthSignupPresenter(val output: iAuthSignupOutput): iEmvpPresenter, iAuthSignupInput, iEditUserProfileOutput {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val editUserProfilePresenter by lazy { EditUserProfilePresenter(this) }

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
                    editUserProfilePresenter.newUser()
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

    override fun onUserCreationSuccessful(userProfile: UserProfile, userSubs: UserSubs) {
        output.onSignUpSuccessful(firebaseAuth.currentUser!!)
    }

    override fun onUserCreationFailure(exception: Exception) {
        handleSignUpFirebaseError(exception)
    }
}