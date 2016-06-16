package com.droidko.voicr.producers.textMessages

import com.droidko.voicr.model.Message
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.error
import org.jetbrains.anko.info


class TextMessagesProducer(val output: iTextMessagesOutput): iTextMessagesInput {

    val databaseRef = FirebaseDatabase.getInstance().reference
    val messagesChangeListener = object: ChildEventListener{
        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            // Do nothing...
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            // Do nothing...
        }

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            output.onMessageArrive(snapshot.getValue(Message::class.java))
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            // Do nothing...
        }

        override fun onCancelled(error: DatabaseError) {
            error { "An error occurred when listening for new messages: ${error.message}" }
        }

    }

    override fun sendMessage(message: String) {

        if (message.isEmpty()) return

        databaseRef
                .child("/posts/")
                .push()
                .setValue(Message(message))
                .addOnSuccessListener { info { "Message $message added" } }
                .addOnFailureListener { exception -> error{ "An error occurred: $exception" } }
    }

    override fun startListeningForMessages() {
        databaseRef.child("/posts/").addChildEventListener(messagesChangeListener)
    }

    override fun stopListeningForMessages() {
        databaseRef.child("/posts/").removeEventListener(messagesChangeListener)
    }

}