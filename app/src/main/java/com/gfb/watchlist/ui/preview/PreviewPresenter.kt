package com.gfb.watchlist.ui.preview

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result
import io.reactivex.Observable

interface PreviewPresenter {

    fun addToList(userId: String, content: Content): Observable<Result>

    fun getContent(contentId: String): Observable<Content>
}