package com.droidko.voicr.presenters.editUserProfile

import com.droidko.voicr.emvp.iEmvpOutput
import com.droidko.voicr.models.UserProfile

interface iEditUserProfileOutput: iEmvpOutput {

    fun onUserProfileCreationSuccessful(userProfile: UserProfile) {
        // Default empty implementation
    }

    fun onUserProfileCreationFailure(exception: Exception) {
        // Default empty implementation
    }

    fun onUserModificationSuccessful(userProfile: UserProfile) {
        // Default empty implementation
    }

    fun onUserModificationFailure(exception: Exception) {
        // Default empty implementation
    }
}