package com.gfb.watchlist.ui.addToList

import com.gfb.watchlist.entity.Content

interface AddToListPresenter {

    fun addContent(userId: String, content: Content)

    fun searchOnImdb(query: String)

    fun showDetails(imdbId: String)

}