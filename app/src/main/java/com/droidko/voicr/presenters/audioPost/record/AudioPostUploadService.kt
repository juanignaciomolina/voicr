package com.droidko.voicr.presenters.audioPost.record

import android.net.Uri
import android.util.Log
import com.droidko.voicr.models.AudioPost
import com.droidko.voicr.presenters.uploads.FileUploadService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference
import java.util.*

class AudioPostUploadService: FileUploadService() {

    val database = FirebaseDatabase.getInstance()
    val cid = "myChannel"
    val uid = FirebaseAuth.getInstance().currentUser!!.uid

    override fun getStoragePath(): StorageReference = storageRef.child("/userProfile-storage/$uid/audios/")

    override fun onFileUploadCompleted(startId: Int, downloadURL: Uri) {
        makeAudioPost(downloadURL)

        super.onFileUploadCompleted(startId, downloadURL) //Remeber to call this for service cleanup
    }

    private fun makeAudioPost(audioUrl: Uri) {

        val pid = getNewPostId()
        val audioPost = AudioPost(audioUrl.toString())

        // Prepare an atomic multi-reference update
        var updates: HashMap<String, Any> = HashMap()
        updates.put("/channel-posts/$cid/$pid/", audioPost.toFbMap())
        updates.put("/userProfile-posts/$uid/$pid/", audioPost.toFbMap())

        database.reference
                .updateChildren(updates)
                .addOnSuccessListener { Log.i(TAG, "AudioPost uploaded with id: $pid") }
                .addOnFailureListener { exception -> Log.e(TAG, "Failure: $exception")}
    }

    private fun getNewPostId() : String {
        return database.getReference("/channel-posts/$cid/").push().key
    }
}