package com.droidko.voicr.mvp

import org.jetbrains.anko.AnkoLogger

/**
 * Base interface for every Presenter of the MVP architecture
 */
interface iMvpPresenter : AnkoLogger {

    /**
     * Return an instance of the MVP Presenter
     */
    open fun getMvpPresenter(): iMvpPresenter {return this}

}