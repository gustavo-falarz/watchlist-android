package com.gfb.watchlist.ui.addToList

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result
import io.reactivex.Observable

interface AddToListView {

    fun onContentAdded(observable: Observable<Result>)

    fun onContentFound(observable: Observable<List<Content>>)

    fun onShowDetails(imdbId: String)

}