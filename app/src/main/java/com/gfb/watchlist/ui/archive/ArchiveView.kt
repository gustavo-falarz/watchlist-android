package com.gfb.watchlist.ui.archive

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result
import io.reactivex.Observable

interface ArchiveView {

    fun getArchive(observable: Observable<List<Content>>)

    fun clearArchive(observable: Observable<Result>)

}