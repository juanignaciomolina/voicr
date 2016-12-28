package com.droidko.voicr.presenters.user.subscriptions

import com.droidko.voicr.emvp.iEmvpInput
import com.droidko.voicr.models.channel.ChannelProfile
import com.google.firebase.database.ChildEventListener

interface iUserSubscriptionsInput : iEmvpInput {

    /**
     * Reactively listens for modifications in the user's subscriptions
     * @param userId: The user ID whose subscriptions will be fetched
     * @param childEventListener: The Firebase listener for changes in the subscriptions node
     */
    fun startListeningForUserSubscriptions(userId: String, childEventListener: ChildEventListener)

    /**
     * Reactively listens for modifications in the logged user's subscriptions
     * @param childEventListener: The Firebase listener for changes in the subscriptions node
     */
    fun startListeningForUserSubscriptions(childEventListener: ChildEventListener)

    /**
     * Statically retrieve a collection of [UserSubs] for a given user
     * @param userId: The user ID whose subscriptions will be fetched
     */
    fun getUserSubscriptions(userId: String)

    /**
     * Statically retrieve a collection of [UserSubs] for the logged user
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