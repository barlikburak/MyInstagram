package com.bb.nst.utils.common

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bb.nst.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Create Image
 */

fun ImageView.downloadImage(url: String?, placeholder: CircularProgressDrawable) {
    val options = RequestOptions().placeholder(placeholder).error(R.drawable.ic_baseline_error_24)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun doPlaceHolder(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 15f
        centerRadius = 40f
        start()
    }
}