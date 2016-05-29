package com.droidko.voicr.mvp

/**
 * Base interface for every Model of the MVP architecture
 */
interface iMvpModel {

    /**
     * Return an instance of the MVP Model
     */
    open fun getMvpModel(): iMvpModel {return this}

}