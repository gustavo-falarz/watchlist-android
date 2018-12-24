package com.gfb.watchlist.ui.preview

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result
import io.reactivex.Observable

interface PreviewView {

    fun onAddContent(observable: Observable<Result>)

    fun onContentAdded(result: Result)

    fun onGetContent(observable: Observable<Content>)
    fun shareContent(): Boolean
    fun addContent()
}