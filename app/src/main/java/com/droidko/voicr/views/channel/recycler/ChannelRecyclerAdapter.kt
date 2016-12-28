package com.droidko.voicr.views.channel.recycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.droidko.voicr.R
import com.droidko.voicr.models.post.AudioPost
import java.util.*

class ChannelRecyclerAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Public vars
    var clickListener: ClickListener? = null

    // Private vars
    private var dataset: ArrayList<AudioPost> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ChannelAudioMessageViewHolder(layoutInflater.inflate(
                R.layout.item_channel_audio_post, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // For now there is only one view type, so we cast it
        holder as ChannelAudioMessageViewHolder

        holder.onBind(dataset.get(position), clickListener)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun addAudioPost(audioPost: AudioPost) {
        dataset.add(0, audioPost)
        notifyItemInserted(0)
    }

    /**
     * Click listener for AudioPost elements in the RecyclerView
     */
    interface ClickListener {
        fun onPlayClicked(post: AudioPost)
        fun onAvatarClicked(post: AudioPost)
    }

}