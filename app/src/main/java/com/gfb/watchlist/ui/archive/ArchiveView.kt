package com.gfb.watchlist.ui.archive

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result
import io.reactivex.Observable

interface ArchiveView {

    fun onGetArchive(observable: Observable<List<Content>>)

    fun onClearArchive(observable: Observable<Result>)

    fun confirmationDelete(): Boolean
    fun getArchive()
}