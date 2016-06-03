package com.droidko.voicr.model

data class Message(
        val message: String,
        val timestamp: Long = System.currentTimeMillis() / 1000L)