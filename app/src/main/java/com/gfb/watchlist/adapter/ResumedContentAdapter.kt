package com.gfb.watchlist.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gfb.watchlist.R
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.util.ImageUtil.load
import kotlinx.android.synthetic.main.adapter_content_resumed.view.*

/**
 * Created by Gustavo on 12/27/2017.
 */
class ResumedContentAdapter(
        private val items: List<Content>,
        private val listener: (Content) -> Unit,
        private val listenerContent: (Content) -> Unit)
    : RecyclerView.Adapter<ResumedContentAdapter.ContentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_content_resumed, parent, false)
        return ContentViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bindView(items[position], listener, listenerContent)
    }

    override fun getItemCount(): Int = items.size

    class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(content: Content, listener: (Content) -> Unit, listenerContent: (Content) -> Unit) = with(itemView) {
            tvTitle.text = content.title
            tvRelease.text = content.year
            btAdd.setOnClickListener { listenerContent(content) }
            setOnClickListener { listener(content) }
            imPoster.load(content.poster) { request -> request.fit() }
        }
    }
}