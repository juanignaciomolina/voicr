package com.droidko.voicr.views.channel.recycler

import android.support.v7.widget.RecyclerView
import android.view.View
import com.droidko.voicr.models.post.AudioPost
import kotlinx.android.synthetic.main.item_channel_audio_post.view.*

class ChannelAudioMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // View holders
    private val avatar = itemView.vAvatar
    private val author = itemView.vAuthor
    private val playButton = itemView.vPlayButton

    fun onBind(audioPost: AudioPost, clickListener: ChannelRecyclerAdapter.ClickListener?) {
        author.text = "by ${audioPost.user.uid}"
        avatar.setOnClickListener { clickListener?.onAvatarClicked(audioPost) }
        playButton.setOnClickListener { clickListener?.onPlayClicked(audioPost) }
    }
}