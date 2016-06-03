package com.droidko.voicr.presenters.home

import com.droidko.voicr.model.Message
import com.droidko.voicr.views.home.iHomeView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.info


class HomePresenter(val homeView: iHomeView): iHomePresenter {

    val databaseRef = FirebaseDatabase.getInstance().reference
    val loggedUser = FirebaseAuth.getInstance().currentUser

    override fun sendMessage(message: String) {

        if (message.isEmpty()) return

        databaseRef
                .child("/posts/user/${loggedUser!!.uid}/")
                .push()
                .setValue(Message(message))
                .addOnSuccessListener { info { "Message $message added" } }
                .addOnFailureListener { exception -> error{ "An error occurred: $exception" } }
    }

}