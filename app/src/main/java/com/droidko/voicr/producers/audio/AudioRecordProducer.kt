package com.droidko.voicr.producers.audio

import android.Manifest
import android.content.Intent
import android.media.MediaRecorder
import android.os.Environment
import com.droidko.voicr.VoicrApplication
import com.droidko.voicr.producers.uploads.FileUploadService
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.multi.EmptyMultiplePermissionsListener
import org.jetbrains.anko.error
import java.util.*

class AudioRecordProducer(val view: iAudioRecordOutput?): iAudioRecordInput {

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
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    fun startMediaRecorder() {

        // Check that the MediaRecorder is not recording, otherwise it will throw an exception
        if (isRecording) return

        // Generate a unique name for the audio file
        val audioUniqueName = UUID.randomUUID().toString() + ".mp4"
        pathToRecordedAudio = Environment.getExternalStorageDirectory().absolutePath + "/$audioUniqueName"

        recorder = MediaRecorder()
        try {
            recorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder?.setAudioSamplingRate(44100);
            recorder?.setAudioEncodingBitRate(96000);
            recorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            recorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            recorder?.setOutputFile(pathToRecordedAudio)
            recorder?.prepare()
            recorder?.start()
        } catch (e: Exception) {
            view?.onRecordFailure()
            error { "MediaRecorder failed on method prepare(). Exception: ${e.message}" }
            freeMediaRecorder()
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
        view?.onRecordSuccessful(pathToRecordedAudio!!) // TODO careful with this cast
    }

    fun freeMediaRecorder() {
        recorder?.release();
        recorder = null;
        isRecording = false
    }

    fun uploadAudioRecord() {
        // Deliver the recorded audio to the service in charge of uploading it
        val intent = Intent(VoicrApplication.instance, AudioPostUploadService::class.java)
        intent.putExtra(FileUploadService.EXTRA_FILE_PATH, pathToRecordedAudio)
        VoicrApplication.instance.startService(intent)
    }

}