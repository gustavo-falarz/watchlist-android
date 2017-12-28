package com.gfb.watchlist.entity

/**
 * Created by Gustavo on 12/27/2017.
 */
object ContentContainer {
    var content: MutableList<Content>? = null

    fun initContent(newContent: MutableList<Content>?) {
        content = newContent!!
    }

    fun getContent(type: String): List<Content> {
        return content!!.filter { it.type == type }
    }

    fun isEmpty(): Boolean {
        return content == null
    }
}