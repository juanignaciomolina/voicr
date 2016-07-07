package com.droidko.voicr.presenters.editUserProfile

import com.droidko.voicr.emvp.iEmvpInput
import com.droidko.voicr.models.UserProfile

interface iEditUserProfileInput: iEmvpInput {

    fun newUserProfile(newUserProfile: UserProfile)

    fun addSubscription(channelId: String)

    fun removeSubscription(channelId: String)

    fun editAvatarUrl(avatarUrl: String)

    fun editUsername(username: String)
}