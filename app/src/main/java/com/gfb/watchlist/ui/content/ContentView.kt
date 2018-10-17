package com.gfb.watchlist.ui.content

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result
import io.reactivex.Observable

interface ContentView {

    fun onArchiveContent(observable: Observable<Result>, content: Content)

    fun deleteContent(result: Result, content: Content)

    fun onContentDeleted()

    fun onShowDetails(content: Content)
}