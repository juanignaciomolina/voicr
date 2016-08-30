package com.droidko.voicr.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.droidko.voicr.R
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.toast

abstract class BaseFragment : Fragment(), AnkoLogger {

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(onLayoutRequested(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null && handleArguments(arguments)) {
            onInitialize(view)
            onPopulateUi(view)
            onSetListeners(view)
        } else {
            toast(getString(R.string.general_error_unknown))
            error { "An error occurred while getting the intent extras.\n" +
                    "Remember to provide extras using the newInstance() method of the Fragment." }
            activity.finish()
        }

    }

    /**
     * Provides a method for a sub class to choose which layout should be loaded in this fragment

     * @return A layout's XML id (e.g: R.layout.example_layout)
     */
    abstract fun onLayoutRequested(): Int


    /**
     * Provides a way for a fragment to get intent extras
     * @param args The bundle obtainable by the getArguments method.
     * @return true if arguments were read successfully, false otherwise.
     * Default implementation returns true.
     */
    open fun handleArguments(arguments: Bundle): Boolean {
        return true
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
    * Lifecycle callback for setting listeners on the UI after being populated
    */
    open fun onSetListeners(rootView: View) {
    }

    /**
     * Shorthand for Context.toast(message: CharSequence)
     */
    fun Fragment.toast(message: CharSequence) {
        context.toast(message)
    }

}
