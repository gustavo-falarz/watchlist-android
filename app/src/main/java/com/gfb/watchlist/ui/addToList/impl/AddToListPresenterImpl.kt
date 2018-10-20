package com.gfb.watchlist.ui.addToList.impl

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.service.ContentService
import com.gfb.watchlist.ui.addToList.AddToListPresenter
import com.gfb.watchlist.ui.addToList.AddToListView

class AddToListPresenterImpl(val view: AddToListView) : AddToListPresenter {
    override fun handleContentFound(content: List<Content>) {
        view.onContentFound(content)
    }

    override fun handleContentAdded(result: Result) {
        ContentContainer.updated = true
        view.onContentAdded(result)
    }

    override fun showDetails(imdbId: String) {
        view.onShowDetails(imdbId)
    }

    override fun addContent(userId: String, content: Content) {
        view.onAddContent(ContentService.addContent(UserContentDTO(userId, content)))
    }

    override fun searchOnImdb(query: String) {
        if (query.isEmpty()) {
            view.onEmptyFields()
        } else {
            view.onFindContent(ContentService.searchOnImdb(query))
        }
    }

}