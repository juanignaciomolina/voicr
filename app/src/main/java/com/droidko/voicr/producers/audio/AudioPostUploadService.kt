package com.droidko.voicr.producers.audio

import android.net.Uri
import android.util.Log
import com.droidko.voicr.model.AudioPost
import com.droidko.voicr.producers.uploads.FileUploadService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference
import java.util.*

class AudioPostUploadService: FileUploadService() {

    val database = FirebaseDatabase.getInstance()
    val cid = "myChannel"
    val uid = FirebaseAuth.getInstance().currentUser!!.uid
    val pid = database.getReference("/channel-posts/$cid/").push().key

    override fun getStoragePath(): StorageReference = storageRef.child("/user-storage/$uid/audios/")

    override fun onFileUploadCompleted(startId: Int, downloadURL: Uri) {
        makeAudioPost(downloadURL)
    }

    private fun makeAudioPost(audioUrl: Uri) {
        val audioPost = AudioPost(audioUrl.toString())

        // Prepare an atomic multi-reference update
        var updates: HashMap<String, Any> = HashMap()
        updates.put("/channel-posts/$cid/$pid/", audioPost.toFbMap())
        updates.put("/user-posts/$uid/$pid/", audioPost.toFbMap())

        database.reference
                .updateChildren(updates)
                .addOnSuccessListener { Log.i(TAG, "AudioPost uploaded: $pid") }
                .addOnFailureListener { exception -> Log.e(TAG, "Failure: $exception")}
    }
}