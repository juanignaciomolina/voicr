package com.droidko.voicr.views.home

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
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
        vChannelsRecycler.layoutManager = GridLayoutManager(context, 2)
        vChannelsRecycler.adapter = channelsAdapter
    }

    override fun onPopulateUi(rootView: View) {
        // TODO REMOVE DUMMY CHANNEL
        val testChannel1 = ChannelProfile(
                cid = "myChannel",
                name = "james bond",
                membersCount = 1,
                avatar = "http://images-cdn.moviepilot.com/image/upload/v1426601393/danielcraigasbond-sorry-idris-why-daniel-craig-isn-t-leaving-james-bond-and-shouldn-t-jpeg-209169.jpg"
        )

        val testChannel2 = ChannelProfile(
                cid = "koolChannel",
                name = "adam levine",
                membersCount = 1,
                avatar = "https://images-na.ssl-images-amazon.com/images/M/MV5BMjI4NjU4MDEwN15BMl5BanBnXkFtZTcwNTQzNDMwNw@@._V1_UX214_CR0,0,214,317_AL_.jpg"
        )

        val testChannel3 = ChannelProfile(
                cid = "ultraChannel",
                name = "michel fassbender",
                membersCount = 1,
                avatar = "http://vignette2.wikia.nocookie.net/twilightsaga/images/f/fd/Michael-fassbender.jpg/revision/latest?cb=20130518210021"
        )

        channelsDataset.add(testChannel1)
        channelsDataset.add(testChannel2)
        channelsDataset.add(testChannel3)
        channelsAdapter.notifyDataSetChanged()
    }

    override fun onSetListeners(rootView: View) {
        vNewChannelButton.setOnClickListener {
            activity.startActivity<NewChannelActivity>()
        }
        vChannelsRecycler.addOnItemTouchListener(object : OnItemClickListener() {
            override fun SimpleOnItemClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {
                activity.startActivity<ChannelActivity>(
                        ChannelFragment.EXTRA_CHANNEL_ID to channelsDataset.get(position).cid
                )
            }
        })
    }
    //endregion

}