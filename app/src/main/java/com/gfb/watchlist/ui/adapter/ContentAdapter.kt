package com.gfb.watchlist.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gfb.watchlist.R
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.util.ImageUtil.load
import kotlinx.android.synthetic.main.adapter_content.view.*

/**
 * Created by Gustavo on 12/22/2017.
 */
class ContentAdapter(private var items: List<Content>, private val listener: (Content) -> Unit, private val watchListener: (Content) -> Unit) : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_content, parent, false)
        return ContentViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bindView(items[position], listener, watchListener)
    }

    override fun getItemCount(): Int = items.size

    class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(content: Content, listener: (Content) -> Unit, watchListener: (Content) -> Unit) = with(itemView) {
            tvTitle.text = content.title
            tvRelease.text = content.year
            tvGenre.text = content.genre
            tvDirector.text = content.director
            imPoster.load(content.poster) { request -> request.fit() }
            btWatch.setOnClickListener { watchListener(content) }
            setOnClickListener { listener(content) }
        }
    }

    fun setItems(items: List<Content>){
        this.items = items
    }
}