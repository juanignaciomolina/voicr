package com.droidko.voicr.views.home

import android.os.Bundle
import android.view.View
import com.droidko.voicr.R
import com.droidko.voicr.views.BaseFragment
import com.droidko.voicr.views.channel.ChannelFragment
import com.droidko.voicr.views.newChannel.NewChannelActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.startActivity

class HomeFragment: BaseFragment() {

    companion object {
        fun newInstance(): HomeFragment {
            val f = HomeFragment()
            val args = Bundle()
            f.arguments = args
            return f
        }
    }

    //region Lifecycle
    override fun onLayoutRequested(): Int {
        return R.layout.fragment_home
    }

    override fun onSetListeners(rootView: View) {
        vNewChannelButton.setOnClickListener {
            activity.startActivity<NewChannelActivity>()
        }
        vDefaultChannel.setOnClickListener {
            // TODO UNHARDCODE THIS
            activity.startActivity<ChannelActivity>(ChannelFragment.EXTRA_CHANNEL_ID to "myChannel")
        }
    }
    //endregion

}