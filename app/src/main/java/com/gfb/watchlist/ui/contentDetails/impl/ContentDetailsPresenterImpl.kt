package com.gfb.watchlist.ui.contentDetails.impl

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.service.ContentService
import com.gfb.watchlist.ui.contentDetails.ContentDetailsPresenter
import com.gfb.watchlist.ui.contentDetails.ContentDetailsView
import com.gfb.watchlist.util.Constants

class ContentDetailsPresenterImpl(val view: ContentDetailsView) : ContentDetailsPresenter {
    override fun populateFields() {
        view.populateFields()
    }

    override fun shareContent(imdbId: String) {
        view.shareContent(Constants.URL_IMDB, imdbId)
    }

    override fun onContentArchived(result: Result) {
        ContentContainer.updated = true
        view.onContentArchived(result)
    }

    override fun onContentDeleted(result: Result) {
        ContentContainer.updated = true
        view.onContentDeleted(result)
    }

    override fun archiveContent(userId: String, content: Content) {
        view.onArchiveContent(ContentService.archiveContent(UserContentDTO(userId, content)))
    }

    override fun deleteContent(userId: String, content: Content) {
        view.onArchiveContent(ContentService.deleteContent(UserContentDTO(userId, content)))
    }

}