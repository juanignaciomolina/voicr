package com.droidko.voicr.views.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.droidko.voicr.R
import com.droidko.voicr.models.ChannelProfile
import com.droidko.voicr.views.BaseFragment
import com.droidko.voicr.views.channel.ChannelFragment
import com.droidko.voicr.views.newChannel.NewChannelActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.startActivity
import java.util.*


class HomeFragment: BaseFragment() {

    companion object {
        fun newInstance(): HomeFragment {
            val f = HomeFragment()
            val args = Bundle()
            f.arguments = args
            return f
        }
    }


    // Vars
    val channelsDataset = ArrayList<ChannelProfile>()
    val channelsAdapter = HomeChannelsAdapter(channelsDataset)

    //region Lifecycle
    override fun onLayoutRequested(): Int {
        return R.layout.fragment_home
    }

    override fun onInitialize(rootView: View) {
        vChannelsRecycler.setHasFixedSize(true)
        vChannelsRecycler.layoutManager = LinearLayoutManager(context)
        vChannelsRecycler.adapter = channelsAdapter
    }

    override fun onPopulateUi(rootView: View) {
        // TODO REMOVE DUMMY CHANNEL
        val myChannel = ChannelProfile(
                "myChannel",
                1,
                "http://images-cdn.moviepilot.com/image/upload/v1426601393/danielcraigasbond-sorry-idris-why-daniel-craig-isn-t-leaving-james-bond-and-shouldn-t-jpeg-209169.jpg"
        )

        channelsDataset.add(myChannel)
        channelsAdapter.notifyItemInserted(0)
    }

    override fun onSetListeners(rootView: View) {
        vNewChannelButton.setOnClickListener {
            activity.startActivity<NewChannelActivity>()
        }
        vChannelsRecycler.addOnItemTouchListener(object : OnItemClickListener() {
            override fun SimpleOnItemClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {
                activity.startActivity<ChannelActivity>(
                        ChannelFragment.EXTRA_CHANNEL_ID to channelsDataset.get(position).name
                )
            }
        })
    }
    //endregion

}