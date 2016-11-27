package com.droidko.voicr.views.main.explore

import android.os.Bundle
import com.droidko.voicr.R
import com.droidko.voicr.views.BaseFragment

class MainExploreFragment : BaseFragment(){

    companion object {
        fun newInstance(): MainExploreFragment {
            val f = MainExploreFragment()
            val args = Bundle()
            f.arguments = args
            return f
        }
    }

    override fun onLayoutRequested(): Int = R.layout.fragment_main_explore
}