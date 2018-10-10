package com.gfb.watchlist.ui.preview.impl

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.service.ContentService
import com.gfb.watchlist.ui.preview.PreviewPresenter
import io.reactivex.Observable

class PreviewPresenterImpl : PreviewPresenter {

    override fun addToList(userId: String, content: Content): Observable<Result> {
        return ContentService.addContent(UserContentDTO(userId, content, null))
    }

    override fun getContent(contentId: String): Observable<Content> {
        return ContentService.getContent(contentId)
    }
}