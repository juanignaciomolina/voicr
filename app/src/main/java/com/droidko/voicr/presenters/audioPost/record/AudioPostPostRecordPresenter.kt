package com.droidko.voicr.presenters.audioPost.record

import android.Manifest
import android.content.Intent
import android.media.MediaRecorder
import com.droidko.voicr.VoicrApplication
import com.droidko.voicr.presenters.uploads.FileUploadService
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.multi.EmptyMultiplePermissionsListener
import org.jetbrains.anko.async
import org.jetbrains.anko.error
import java.io.File
import java.util.*

class AudioPostPostRecordPresenter(val output: iAudioPostRecordOutput?): iAudioPostRecordInput {

    val TEMPORARY_FILES_PATH = "pending-uploads"
    val AUDIO_SAMPLING_RATE = 44100
    val AUDIO_ENCODING_BITRATE = 96000

    var recorder: MediaRecorder? = null
    var isRecording: Boolean = false
    var pathToRecordedAudio: String? = null

    override fun startRecording() {

        // Check runtime permissions
        Dexter.checkPermissions(
                object: EmptyMultiplePermissionsListener() {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (report.areAllPermissionsGranted()) {
                            startMediaRecorder()
                        }
                    }
                },
                Manifest.permission.RECORD_AUDIO)
    }

    fun startMediaRecorder() {

        // Check that the MediaRecorder is not recording, otherwise it will throw an exception
        if (isRecording) return

        async() {

            // Set up an internal directory if it doesn't exist already
            val pendingUploadsDir = File(VoicrApplication.instance.filesDir, "$TEMPORARY_FILES_PATH")
            if (!pendingUploadsDir.exists()) pendingUploadsDir.mkdir()

            // Generate a unique name for the audio file
            val audioUniqueName = UUID.randomUUID().toString() + ".aac"
            val audioFile = File("${VoicrApplication.instance.filesDir}/$TEMPORARY_FILES_PATH/", audioUniqueName);

            pathToRecordedAudio = audioFile.absolutePath

            recorder = MediaRecorder()
            try {
                recorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
                recorder?.setAudioSamplingRate(AUDIO_SAMPLING_RATE);
                recorder?.setAudioEncodingBitRate(AUDIO_ENCODING_BITRATE);
                recorder?.setAudioChannels(1)
                recorder?.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
                recorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                recorder?.setOutputFile(pathToRecordedAudio)
                recorder?.prepare()
                recorder?.start()
            } catch (e: Exception) {
                output?.onRecordFailure()
                error { "MediaRecorder failed on method prepare(). Exception: ${e.message}" }
                freeMediaRecorder()
            }
        }

        isRecording = true
    }

    override fun stopRecording() {

        // Check that the MediaRecorder is recording, otherwise it will throw an exception
        if (!isRecording) return

        try {
            recorder?.stop();
        } catch(e: Exception) {
            error { "MediaRecorder failed on method stop(). Exception: ${e.message}" }
        } finally {
            freeMediaRecorder()
        }

        uploadAudioRecord()
        output?.onRecordSuccessful(pathToRecordedAudio!!) // Careful with this cast
    }

    fun freeMediaRecorder() {
        recorder?.release();
        recorder = null;
        isRecording = false
    }

    fun uploadAudioRecord() {
        // Deliver the recorded audio to the service in charge of uploading it
        val intent = Intent(VoicrApplication.Companion.instance, AudioPostUploadService::class.java)
        intent.putExtra(FileUploadService.EXTRA_FILE_PATH, pathToRecordedAudio)
        VoicrApplication.instance.startService(intent)
    }

}