package com.droidko.voicr

import android.app.Application
import com.karumi.dexter.Dexter

class VoicrApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        setUpRuntimePermissions()
    }

    private fun setUpRuntimePermissions() {
        Dexter.initialize(this)
    }
}