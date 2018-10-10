package com.gfb.watchlist.ui.content.impl

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.service.ContentService
import com.gfb.watchlist.ui.content.ContentPresenter
import io.reactivex.Observable

class ContentPresenterImpl : ContentPresenter {

    override fun archiveContent(userId: String, content: Content): Observable<Result> {
        return ContentService.archiveContent(UserContentDTO(userId, content))
    }

}