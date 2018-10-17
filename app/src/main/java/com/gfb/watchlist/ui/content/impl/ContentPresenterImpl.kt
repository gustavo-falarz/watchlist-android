package com.gfb.watchlist.ui.content.impl

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.service.ContentService
import com.gfb.watchlist.ui.content.ContentPresenter
import com.gfb.watchlist.ui.content.ContentView

class ContentPresenterImpl(val view: ContentView) : ContentPresenter {

    override fun callActivity(content: Content) {
        view.callActivity(content)
    }

    override fun archiveContent(userId: String, content: Content) {
        view.onContentArchived(ContentService.archiveContent(UserContentDTO(userId)), content)
    }

    override fun deleteContent(content: Content) {
        ContentContainer.content.remove(content)
        view.onContentDeleted()
    }

}