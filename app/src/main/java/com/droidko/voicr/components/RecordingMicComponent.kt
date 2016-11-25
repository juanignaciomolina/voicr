package com.droidko.voicr.components

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.droidko.voicr.Configuration
import com.droidko.voicr.R
import kotlinx.android.synthetic.main.view_recording_mic.view.*
import org.jetbrains.anko.AnkoLogger
import org.joda.time.Duration
import org.joda.time.Period
import org.joda.time.format.PeriodFormatterBuilder



class RecordingMicComponent : LinearLayout, AnkoLogger {

    val TIMER_REFRESH_INTERVAL: Long = 100

    var timeRemaining = Period()
    val secondsAndMilisecondsFormatter = PeriodFormatterBuilder()
            .printZeroAlways()
            .appendSecondsWithMillis()
            .toFormatter()

    val counterHandler = Handler()
    val runnableCounter = object : Runnable {
        override fun run() {
            timeRemaining = timeRemaining.minusMillis(TIMER_REFRESH_INTERVAL.toInt())
            vCountdownTimer.text = secondsAndMilisecondsFormatter.print(timeRemaining)

            counterHandler.postDelayed(this, TIMER_REFRESH_INTERVAL)
        }
    }

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.view_recording_mic, this);
    }

    fun startCounter() {
        timeRemaining = Duration(0, Configuration.MAX_AUDIO_POST_LENGTH_MILLIS.toLong()).toPeriod()
        counterHandler.post(runnableCounter)
    }

    fun stopCounter() {
        counterHandler.removeCallbacks(runnableCounter)
    }
}