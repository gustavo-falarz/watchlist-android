package com.gfb.watchlist.ui.content

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result
import io.reactivex.Observable

interface ContentPresenter {

    fun archiveContent(userId: String, content: Content): Observable<Result>

}