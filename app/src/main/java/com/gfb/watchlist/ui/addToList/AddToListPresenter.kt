package com.gfb.watchlist.ui.addToList

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result

interface AddToListPresenter {

    fun addContent(userId: String, content: Content)

    fun searchOnImdb(query: String)

    fun showDetails(imdbId: String)

    fun handleContentAdded(result: Result)

    fun handleContentFound(content: List<Content>)
}