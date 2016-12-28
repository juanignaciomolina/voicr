package com.droidko.voicr.models

import java.util.*

interface iFirebaseModel {

    /**
     * Maps the object to a serializable HashMap that can be stored in the Firebae Database
     */
    fun toFbMap() : HashMap<String, Any>

}