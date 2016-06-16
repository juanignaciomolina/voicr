package com.droidko.voicr.consumers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast

abstract class BaseFragment : Fragment(), AnkoLogger {

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(onLayoutRequested(), container, false)
    }

    /**
     * Provides a method for a sub class to choose which layout should be loaded in this fragment

     * @return A layout's XML id (e.g: R.layout.example_layout)
     */
    abstract fun onLayoutRequested(): Int

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (view != null) {
            onInitialize(view)
            onPopulateUi(view)
        }
    }

    /**
     * Lifecycle callback for fragment initialization
     */
    open fun onInitialize(rootView: View) {
    }

    /**
     * Lifecycle callback for making modifications on the UI after the initialization
     */
    open fun onPopulateUi(rootView: View) {
    }

    /**
     * Shorthand for Context.toast(message: CharSequence)
     */
    fun Fragment.toast(message: CharSequence) {
        context.toast(message)
    }

    /**
     * Shorthand for Context.toast(textResource: Int)
     */
    fun Fragment.toast(textResource: Int) {
        context.toast(textResource)
    }

}
