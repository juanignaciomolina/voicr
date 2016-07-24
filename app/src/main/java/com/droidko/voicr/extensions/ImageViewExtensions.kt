package com.droidko.voicr.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Loads a given image URL into the ImageView using Glide
 */
fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).into(this)
}