package com.droidko.voicr

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
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
        setUpFirebase()
    }

    private fun setUpRuntimePermissions() {
        Dexter.initialize(this)
    }

    private fun setUpFirebase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}