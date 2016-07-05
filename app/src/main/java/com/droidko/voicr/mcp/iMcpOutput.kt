package com.droidko.voicr.mcp

/**
 * Base interface for every View of the MVP architecture
 */
interface iMcpOutput {

    /**
     * Return an instance of the MVP View
     */
    open fun getMvpView(): iMcpOutput {return this}

}