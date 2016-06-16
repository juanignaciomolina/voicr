package com.droidko.voicr

import android.app.Application
import com.karumi.dexter.Dexter

class VoicrApplication: Application() {

    companion object {
        lateinit var instance: VoicrApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        setUpRuntimePermissions()
    }

    private fun setUpRuntimePermissions() {
        Dexter.initialize(this)
    }
}