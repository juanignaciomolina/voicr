package com.droidko.voicr.views.main.channels

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.droidko.voicr.R
import com.droidko.voicr.models.channel.ChannelProfile
import com.facebook.drawee.view.SimpleDraweeView


class MainChannelsAdapter(dataset : List<ChannelProfile>) :
        BaseQuickAdapter<ChannelProfile, BaseViewHolder>(R.layout.item_home_channel, dataset) {

    override fun convert(viewHolder: BaseViewHolder, item: ChannelProfile) {
        viewHolder.getView<SimpleDraweeView>(R.id.vAvatar).setImageURI(item.avatar)
        viewHolder.getView<TextView>(R.id.vAuthor).text = item.name
        viewHolder.getView<TextView>(R.id.vMembersCount).text =  "${item.membersCount} followers"
    }
}