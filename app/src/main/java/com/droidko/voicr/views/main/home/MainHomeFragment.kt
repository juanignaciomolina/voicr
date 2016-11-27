package com.droidko.voicr.views.main.home

import android.os.Bundle
import com.droidko.voicr.R
import com.droidko.voicr.views.BaseFragment

class MainHomeFragment : BaseFragment(){

    companion object {
        fun newInstance(): MainHomeFragment {
            val f = MainHomeFragment()
            val args = Bundle()
            f.arguments = args
            return f
        }
    }

    override fun onLayoutRequested(): Int = R.layout.fragment_main_home
}