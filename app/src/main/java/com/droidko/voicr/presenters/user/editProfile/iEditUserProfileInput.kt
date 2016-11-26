package com.droidko.voicr.presenters.user.editProfile

import com.droidko.voicr.emvp.iEmvpInput
import com.droidko.voicr.models.UserProfile

interface iEditUserProfileInput: iEmvpInput {

    /**
     * Register a new [UserProfile] and [UserSubs] on the server side
     */
    fun newUser()

    /**
     * Update the logged user profile avatar by providing a suitable URL
     * @param avatarUrl: The new avatar's URL for the logged user
     */
    fun editAvatarUrl(avatarUrl: String)

    /**
     * Update the logged user profile name
     * @param username: The new username for the logged user
     */
    fun editUsername(username: String)
}