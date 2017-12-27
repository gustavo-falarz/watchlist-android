package com.gfb.watchlist.util

import android.content.Context
import android.widget.ImageView
import com.gfb.watchlist.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

/**
 * Created by Gustavo on 12/27/2017.
 */
object ImageUtil {

    fun ImageView.load(path: String, request: (RequestCreator) -> RequestCreator) {
        request(context.picasso.load(path)).placeholder(R.drawable.ic_poster_placeholder).into(this)
    }

    private val Context.picasso: Picasso
        get() = Picasso.with(this)
}