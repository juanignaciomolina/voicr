package com.droidko.voicr.model

import com.google.firebase.auth.FirebaseAuth

data class Message(
        val message: String = "",
        val userId: String = FirebaseAuth.getInstance().currentUser!!.uid,
        val userEmail: String? = FirebaseAuth.getInstance().currentUser!!.email,
        val timestamp: Long = System.currentTimeMillis() / 1000L)