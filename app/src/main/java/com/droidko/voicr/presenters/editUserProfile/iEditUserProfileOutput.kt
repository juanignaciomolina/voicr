package com.droidko.voicr.presenters.editUserProfile

import com.droidko.voicr.emvp.iEmvpOutput
import com.droidko.voicr.models.UserProfile

interface iEditUserProfileOutput: iEmvpOutput {

    /**
     * Callback triggered after a successful user's server side profile creation
     * @param userProfile: A [UserProfile] instance of the newly created server side user's profile
     */
    fun onUserProfileCreationSuccessful(userProfile: UserProfile) {
        // Default empty implementation, override if needed
    }

    /**
     * Callback triggered after an unsuccessful user's server side profile creation
     * @param exception: An [Exception] with the creation error
     */
    fun onUserProfileCreationFailure(exception: Exception) {
        // Default empty implementation, override if needed
    }

    /**
     * Callback triggered after a successful User's server side profile modification
     * @param userProfile: An updated [UserProfile]
     */
    fun onUserModificationSuccessful(userProfile: UserProfile) {
        // Default empty implementation, override if needed
    }

    /**
     * Callback triggered after an unsuccessful user's server side profile modification
     * @param exception: An [Exception] with the modification error
     */
    fun onUserModificationFailure(exception: Exception) {
        // Default empty implementation, override if needed
    }
}