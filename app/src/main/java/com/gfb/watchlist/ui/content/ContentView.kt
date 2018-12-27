package com.gfb.watchlist.ui.content

import android.text.TextWatcher
import android.view.View
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result
import io.reactivex.Observable

interface ContentView {

    fun onArchiveContent(observable: Observable<Result>, content: Content)

    fun deleteContent(result: Result, content: Content)

    fun onContentDeleted()

    fun onShowDetails(content: Content)

    fun updateList(items: List<Content>)

    fun onSearch(): TextWatcher?

    fun createAdapter(list: List<Content>)

    fun setupAdapter()

    fun showDetails(content: Content)

    fun confirmationArchive(content: Content)

    fun archiveContent(content: Content)
}