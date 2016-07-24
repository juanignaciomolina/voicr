package com.droidko.voicr.presenters.editUserProfile

import com.droidko.voicr.emvp.iEmvpInput
import com.droidko.voicr.models.UserProfile

interface iEditUserProfileInput: iEmvpInput {

    /**
     * Create a brand new user's profile on the server
     * @param newUserProfile: An instance of a [UserProfile] to be stored server side
     */
    fun newUserProfile(newUserProfile: UserProfile)

    /**
     * Subscribe the current logged user to a channel
     * @param channelId: The ID of the channel that the user will be subscribed to
     */
    fun addSubscription(channelId: String)

    /**
     * Unsubscribes the current logged user from a given channel
     * @param channelId: The ID of the channel that the user will be unsubscribed from
     */
    fun removeSubscription(channelId: String)

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