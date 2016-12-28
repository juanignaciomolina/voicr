package com.droidko.voicr.presenters.user.subscriptionsMessages

import com.droidko.voicr.emvp.iEmvpInput
import com.droidko.voicr.presenters.user.subscriptions.iUserSubscriptionsInput
import com.google.firebase.database.ChildEventListener

interface iUserSubscriptionsMessagesInput : iEmvpInput, iUserSubscriptionsInput {

    /**
     * Reactively listens for modifications of each one of the user's subscriptions messages
     * @param userId: The user ID whose subscriptions will be fetched
     * @param childEventListener: The Firebase listener for changes in the subscriptions node
     */
    fun startListeningForUserSubscriptionsMessages(userId: String, childEventListener: ChildEventListener)

    /**
     * Reactively listens for modifications of each one of the logged user's subscriptions messages
     * @param childEventListener: The Firebase listener for changes in the subscriptions node
     */
    fun startListeningForUserSubscriptionsMessages(childEventListener: ChildEventListener)

}