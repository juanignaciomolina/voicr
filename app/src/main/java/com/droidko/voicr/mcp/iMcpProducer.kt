package com.droidko.voicr.mcp

import org.jetbrains.anko.AnkoLogger

/**
 * Base interface for every Presenter of the MVP architecture
 */
interface iMcpProducer : AnkoLogger {

    /**
     * Return an instance of the MVP Presenter
     */
    open fun getMvpPresenter(): iMcpProducer {return this}

}