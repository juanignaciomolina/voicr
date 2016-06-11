package com.droidko.voicr.presenters.audio

import android.Manifest
import android.media.MediaRecorder
import android.os.Environment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.multi.EmptyMultiplePermissionsListener
import org.jetbrains.anko.error

class AudioRecordPresenter(val view: iAudioRecordView?): iAudioRecordPresenter {

    var recorder: MediaRecorder? = null
    var isRecording: Boolean = false
    val pathToRecordedAudio by lazy {
        Environment.getExternalStorageDirectory().absolutePath + "/audiorecordtest.mp4"
    }

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
            isRecording = true
        } catch (e: Exception) {
            view?.onRecordFailure()
            error { "MediaRecorder failed on method prepare(). Exception: ${e.message}" }
            freeMediaRecorder()
        }
    }

    override fun stopRecording() {

        // Check that the MediaRecorder is recording, otherwise it will throw an exception
        if (!isRecording) return

        try {
            recorder?.stop();
            view?.onRecordSuccessful(pathToRecordedAudio)
        } catch(e: Exception) {
            error { "MediaRecorder failed on method stop(). Exception: ${e.message}" }
        } finally {
            freeMediaRecorder()
        }
    }

    fun freeMediaRecorder() {
        recorder?.release();
        recorder = null;
        isRecording = false
    }

}