package com.droidko.voicr.presenters.uploads

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.util.Log
import com.droidko.voicr.firebase.StorageAccess
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import org.jetbrains.anko.async
import java.io.File

abstract class FileUploadService : Service() {

    companion object {
        val EXTRA_FILE_PATH = "filePath"
        val TAG = "UploadService"
    }

    val storageAccess = StorageAccess()

    //region Service lifecycle
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent != null && intent.hasExtra(EXTRA_FILE_PATH)) {
            startUploading(intent.getStringExtra(EXTRA_FILE_PATH), startId)
        } else {
            Log.e(TAG, "A file path must be provided with the extra EXTRA_FILE_PATH")
            stopSelf(startId)
        }

        //return START_REDELIVER_INTENT
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        // We don't provide binding, so return null
        return null
    }

    override fun onDestroy() {
        Log.i(TAG, "Bye Bye")
        super.onDestroy()
    }
    //endregion

    /**
     * Callback for child classes called after the generic upload has been completed
     */
    open fun onFileUploadCompleted(startId: Int, downloadURL: Uri) {
        stopSelf(startId)
    }

    /**
     * Provides a path where the files are uploaded to
     */
    abstract fun getStoragePath(): StorageReference

    fun startUploading(filePath: String, startId: Int) {
        async() {
            val fileUri = Uri.fromFile(File(filePath));
            val uploadRef = getStoragePath().child(fileUri.lastPathSegment);
            val uploadTask = uploadRef.putFile(fileUri);

            setUploadListeners(uploadTask, filePath, startId)
        }
    }

    fun setUploadListeners(task: UploadTask, filePath: String, startId: Int) {
        task.addOnSuccessListener({ snapshot ->
            Log.i(TAG, "Done! ${snapshot.downloadUrl}")
            deleteLocalAudioFile(filePath)
            onFileUploadCompleted(startId, snapshot.downloadUrl!!)
        })

        task.addOnFailureListener({ exception ->
            Log.e(TAG, "Error: $exception")
            stopSelf(startId)
        })

        task.addOnProgressListener({ snapshot ->
            val progress = (snapshot.bytesTransferred * 100) / snapshot.totalByteCount
            Log.i(TAG, "Uploading... $progress%")
        })
    }

    fun deleteLocalAudioFile(filePath: String) {
        File(filePath).delete()
    }
}