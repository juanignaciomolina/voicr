package com.droidko.voicr.mcp

/**
 * Base interface for every View of the MVP architecture
 */
interface iMcpConsumer {

    /**
     * Return an instance of the MVP View
     */
    open fun getMvpView(): iMcpConsumer {return this}

}