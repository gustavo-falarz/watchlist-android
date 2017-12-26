package com.gfb.watchlist.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gfb.watchlist.R
import com.gfb.watchlist.entity.Content
import kotlinx.android.synthetic.main.adapter_content.view.*

/**
 * Created by Gustavo on 12/22/2017.
 */
class ContentAdapter(private val items: List<Content>, private val listener: (Content) -> Unit) : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ContentViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.adapter_content, parent, false)
        return ContentViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContentViewHolder?, position: Int) {
        if (holder != null) {
            holder.bindView(items[position], listener)
        }
    }

    override fun getItemCount(): Int = items.size

    class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(content: Content, listener: (Content) -> Unit) = with(itemView) {
            contentTitle.text = content.title
            contentRelease.text = content.year
            setOnClickListener { listener(content) }
        }
    }
}