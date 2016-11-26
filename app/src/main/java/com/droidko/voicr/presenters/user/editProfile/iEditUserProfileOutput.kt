package com.droidko.voicr.presenters.user.editProfile

import com.droidko.voicr.emvp.iEmvpOutput
import com.droidko.voicr.models.UserProfile
import com.droidko.voicr.models.UserSubs

interface iEditUserProfileOutput: iEmvpOutput {

    /**
     * Callback triggered after a successful creation of a user server side
     * @param userProfile: A [UserProfile] instance of the newly created user on the server
     *@param userSubs: A [UserSubs] instance of the newly created user on the server
     */
    fun onUserCreationSuccessful(userProfile: UserProfile, userSubs: UserSubs) {
        // Default empty implementation, override if needed
    }

    /**
     * Callback triggered after an unsuccessful creation of a new user server side
     * @param exception: An [Exception] with the creation error
     */
    fun onUserCreationFailure(exception: Exception) {
        // Default empty implementation, override if needed
    }

    /**
     * Callback triggered after a successful User's server side profile modification
     * @param userProfile: An updated [UserProfile]
     */
    fun onUserProfileModificationSuccessful(userProfile: UserProfile) {
        // Default empty implementation, override if needed
    }

    /**
     * Callback triggered after an unsuccessful user's server side profile modification
     * @param exception: An [Exception] with the modification error
     */
    fun onUserProfileModificationFailure(exception: Exception) {
        // Default empty implementation, override if needed
    }
}