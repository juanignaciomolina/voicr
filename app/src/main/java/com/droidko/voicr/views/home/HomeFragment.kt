package com.droidko.voicr.views.home

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.droidko.voicr.R
import com.droidko.voicr.models.ChannelProfile
import com.droidko.voicr.models.UserSubs
import com.droidko.voicr.presenters.user.subscriptions.UserSubscriptionsPresenter
import com.droidko.voicr.presenters.user.subscriptions.iUserSubscriptionsOutput
import com.droidko.voicr.views.BaseFragment
import com.droidko.voicr.views.channel.ChannelFragment
import com.droidko.voicr.views.newChannel.NewChannelActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*


class HomeFragment: BaseFragment(), iUserSubscriptionsOutput {

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
    val subscriptionsPresenter by lazy { UserSubscriptionsPresenter(this) }

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
        subscriptionsPresenter.getUserSubscriptions()
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

    //region Subscriptions
    override fun onUserSubsGetSuccessful(userSubs: UserSubs) {
        channelsDataset.addAll(userSubs.subscriptions.values)
        channelsAdapter.notifyDataSetChanged()
    }

    override fun onUserSubsGetFailure(exception: Exception) {
        activity.toast("An error occurred while retrieving the subscriptions")
    }
    //endregion
}