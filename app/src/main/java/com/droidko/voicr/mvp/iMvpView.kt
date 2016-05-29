package com.droidko.voicr.mvp

/**
 * Base interface for every View of the MVP architecture
 */
interface iMvpView {

    /**
     * Return an instance of the MVP View
     */
    open fun getMvpView(): iMvpView {return this}

}