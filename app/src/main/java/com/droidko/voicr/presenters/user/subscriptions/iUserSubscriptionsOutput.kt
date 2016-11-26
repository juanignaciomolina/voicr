package com.droidko.voicr.presenters.user.subscriptions

import com.droidko.voicr.emvp.iEmvpOutput
import com.droidko.voicr.models.UserSubs

interface iUserSubscriptionsOutput : iEmvpOutput {

    /**
     * Callback triggered after a successful user's subscriptions retrieval
     * @param userSubs: The retrieved [UserSubs]
     */
    fun onUserSubsGetSuccessful(userSubs: UserSubs) {
        // Default empty implementation, override if needed
    }

    /**
     * Callback triggered after an unsuccessful user's subscriptions retrieval
     * @param exception: An [Exception] with the request error
     */
    fun onUserSubsGetFailure(exception: Exception) {
        // Default empty implementation, override if needed
    }

    /**
     * Callback triggered after a successful user's server side subscriptions modification
     * @param userSubs: An updated [UserSubs]
     */
    fun onUserSubsModificationSuccessful(userSubs: UserSubs) {
        // Default empty implementation, override if needed
    }

    /**
     * Callback triggered after an unsuccessful user's server side subscriptions modification
     * @param exception: An [Exception] with the modification error
     */
    fun onUserSubsModificationFailure(exception: Exception) {
        // Default empty implementation, override if needed
    }
}