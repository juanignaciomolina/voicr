package com.droidko.voicr.mcp

/**
 * Base interface for every Model of the MVP architecture
 */
interface iMcpModel {

    /**
     * Return an instance of the MVP Model
     */
    open fun getMvpModel(): iMcpModel {return this}

}