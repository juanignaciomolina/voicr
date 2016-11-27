package com.droidko.voicr.views.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.droidko.voicr.views.main.channels.MainChannelsFragment
import com.droidko.voicr.views.main.explore.MainExploreFragment
import com.droidko.voicr.views.main.home.MainHomeFragment

class MainFragmentsAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    val fragments = arrayListOf(
            MainHomeFragment.newInstance(),
            MainExploreFragment.newInstance(),
            MainChannelsFragment.newInstance()
    )

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]
}