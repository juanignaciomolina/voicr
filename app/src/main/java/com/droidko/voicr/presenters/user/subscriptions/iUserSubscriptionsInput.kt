package com.droidko.voicr.presenters.user.subscriptions

import com.droidko.voicr.emvp.iEmvpInput
import com.droidko.voicr.models.ChannelProfile

interface iUserSubscriptionsInput : iEmvpInput {

    /**
     * Retrieve a collection of [UserSubs] for a given user
     * @param userId: The user ID whose subscriptions will be fetched
     */
    fun getUserSubscriptions(userId: String)

    /**
     * Retrieve a collection of [UserSubs] for the logged user
     */
    fun getUserSubscriptions()

    /**
     * Subscribe the current logged user to a channel
     * @param channelId: The ID of the channel that the user will be subscribed to
     */
    fun addSubscription(channelProfile: ChannelProfile)

    /**
     * Unsubscribes the current logged user from a given channel
     * @param channelId: The ID of the channel that the user will be unsubscribed from
     */
    fun removeSubscription(channelProfile: ChannelProfile)
}