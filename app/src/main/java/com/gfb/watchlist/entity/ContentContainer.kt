package com.gfb.watchlist.entity

/**
 * Created by Gustavo on 12/27/2017.
 */
object ContentContainer {
    var content: MutableList<Content> = mutableListOf()
    var updated: Boolean = true

    fun initContent(newContent: MutableList<Content>) {
        content = newContent
    }

    fun getContent(type: String): List<Content> {
        return content.filter { it.type == type }
    }

}