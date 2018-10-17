package com.gfb.watchlist.ui.addToList.impl

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.service.ContentService
import com.gfb.watchlist.ui.addToList.AddToListPresenter
import com.gfb.watchlist.ui.addToList.AddToListView

class AddToListPresenterImpl(val view: AddToListView) : AddToListPresenter {

    override fun showDetails(imdbId: String) {
        view.onShowDetails(imdbId)
    }

    override fun addContent(userId: String, content: Content) {
        view.onContentAdded(ContentService.addContent(UserContentDTO(userId, content)))
    }

    override fun searchOnImdb(query: String) {
        view.onContentFound(ContentService.searchOnImdb(query))
    }

}