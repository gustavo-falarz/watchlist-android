package com.gfb.watchlist.ui.preview.impl

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.service.ContentService
import com.gfb.watchlist.ui.preview.PreviewPresenter
import com.gfb.watchlist.ui.preview.PreviewView
import io.reactivex.Observable

class PreviewPresenterImpl(val view: PreviewView) : PreviewPresenter {
    override fun addContent(userId: String, content: Content) {
        view.onAddContent(ContentService.addContent(UserContentDTO(userId, content, null)))
    }

    override fun onContentAdded(result: Result) {
        ContentContainer.updated = true
        view.onContentAdded(result)
    }

    override fun getContent(contentId: String) {
        view.onGetContent(ContentService.getContent(contentId))
    }
}