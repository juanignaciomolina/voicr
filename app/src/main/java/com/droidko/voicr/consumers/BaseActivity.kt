package com.droidko.voicr.consumers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.droidko.voicr.R
import kotlinx.android.synthetic.main.activity_base.*


import org.jetbrains.anko.AnkoLogger

abstract class BaseActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        supportFragmentManager
                .beginTransaction()
                .add(vFragmentContainer.id, onFragmentRequested())
                .commit()
    }

    /**
     * Provides a method for a sub class to choose which fragment should be loaded in this activity
     *
     * @return A Fragment to be loaded
     */
    protected abstract fun onFragmentRequested(): Fragment

}
