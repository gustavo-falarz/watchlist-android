package com.gfb.watchlist.ui.content.impl

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.service.ContentService
import com.gfb.watchlist.ui.content.ContentPresenter
import com.gfb.watchlist.ui.content.ContentView

class ContentPresenterImpl(val view: ContentView) : ContentPresenter {

    override fun showDetails(content: Content) {
        view.onShowDetails(content)
    }

    override fun archiveContent(userId: String, content: Content) {
        view.onArchiveContent(ContentService.archiveContent(UserContentDTO(userId)), content)
    }

    override fun deleteContent(content: Content) {
        ContentContainer.content.remove(content)
        view.onContentDeleted()
    }

}