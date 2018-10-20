package com.gfb.watchlist.ui.addToList

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result
import io.reactivex.Observable

interface AddToListView {

    fun onAddContent(observable: Observable<Result>)

    fun onFindContent(observable: Observable<List<Content>>)

    fun onShowDetails(imdbId: String)

    fun onContentAdded(result: Result)

    fun onContentFound(contents: List<Content>)

    fun onEmptyFields()
}