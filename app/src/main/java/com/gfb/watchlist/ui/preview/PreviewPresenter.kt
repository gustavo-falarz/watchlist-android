package com.gfb.watchlist.ui.preview

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result
import io.reactivex.Observable

interface PreviewPresenter {

    fun addContent(userId: String, content: Content)

    fun onContentAdded(result: Result)

    fun getContent(contentId: String)

}