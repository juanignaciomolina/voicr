package com.droidko.voicr.presenters.audioPost.record

import android.net.Uri
import android.util.Log
import com.droidko.voicr.firebase.DbAccess
import com.droidko.voicr.models.AudioPost
import com.droidko.voicr.presenters.uploads.FileUploadService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference
import java.util.*

class AudioPostUploadService: FileUploadService() {

    val database = FirebaseDatabase.getInstance()
    val dbAccess = DbAccess()
    val cid = "myChannel" // TODO Allow posting in other channels
    val uid = FirebaseAuth.getInstance().currentUser!!.uid

    override fun getStoragePath(): StorageReference = storageAccess.userAudios()

    override fun onFileUploadCompleted(startId: Int, downloadURL: Uri) {
        makeAudioPost(downloadURL)

        super.onFileUploadCompleted(startId, downloadURL) //Remeber to call this for service cleanup
    }

    private fun makeAudioPost(audioUrl: Uri) {

        val pid = getNewPostId()
        val audioPost = AudioPost(audioUrl.toString())

        // Prepare an atomic multi-reference update
        var updates: HashMap<String, Any> = HashMap()
        updates.put("/${DbAccess.PATH_CHANNEL_POSTS}/$cid/$pid/", audioPost.toFbMap())
        updates.put("/${DbAccess.PATH_USER_POSTS}/$uid/$pid/", audioPost.toFbMap())

        database.reference
                .updateChildren(updates)
                .addOnSuccessListener { Log.i(TAG, "AudioPost uploaded with id: $pid") }
                .addOnFailureListener { exception -> Log.e(TAG, "Failure: $exception")}
    }

    private fun getNewPostId() : String {
        return dbAccess.channelPosts(cid).push().key
    }
}