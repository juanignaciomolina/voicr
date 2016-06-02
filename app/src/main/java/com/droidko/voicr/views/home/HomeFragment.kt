package com.droidko.voicr.views.home

import com.droidko.voicr.R
import com.droidko.voicr.views.BaseFragment

class HomeFragment: BaseFragment(), iHomeView {

    override fun onLayoutRequested(): Int {
        return R.layout.fragment_home
    }

}