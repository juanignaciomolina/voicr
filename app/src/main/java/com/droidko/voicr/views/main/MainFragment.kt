package com.droidko.voicr.views.main

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.droidko.voicr.R
import com.droidko.voicr.views.BaseFragment
import com.droidko.voicr.views.newChannel.NewChannelActivity
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.startActivity

class MainFragment : BaseFragment() {

    companion object {
        fun newInstance(): MainFragment {
            val f = MainFragment()
            val args = Bundle()
            f.arguments = args
            return f
        }
    }

    override fun onLayoutRequested(): Int = R.layout.fragment_main

    override fun onPopulateUi(rootView: View) {
        vFragmentsViewPager.adapter = MainFragmentsAdapter(fragmentManager)
    }

    override fun onSetListeners(rootView: View) {
        vNewChannelButton.setOnClickListener {
            activity.startActivity<NewChannelActivity>()
        }

        vNavigationBottomBar.setOnTabSelectListener { tabId ->
            when(tabId) {
                R.id.vNavigationTabHome -> vFragmentsViewPager.currentItem = 0
                R.id.vNavigationTabExplore -> vFragmentsViewPager.currentItem = 1
                R.id.vNavigationTabSubscriptions -> vFragmentsViewPager.currentItem = 2
            }
        }

        vFragmentsViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) = Unit

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> vNavigationBottomBar.selectTabWithId(R.id.vNavigationTabHome)
                    1 -> vNavigationBottomBar.selectTabWithId(R.id.vNavigationTabExplore)
                    2 -> vNavigationBottomBar.selectTabWithId(R.id.vNavigationTabSubscriptions)
                }
            }
        })
    }
}