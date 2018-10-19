package com.gfb.watchlist.ui.contentDetails

import com.gfb.watchlist.entity.Result
import io.reactivex.Observable

interface ContentDetailsView {

    fun onArchiveContent(observable: Observable<Result>)

    fun onContentArchived(result: Result)

    fun onDeleteContent(observable: Observable<Result>)

    fun onContentDeleted(result: Result)

    fun shareContent(imdbUrl: String, imdbId: String)

    fun populateFields()
}