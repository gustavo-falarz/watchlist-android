package com.gfb.watchlist.ui.main

import com.gfb.watchlist.entity.Content
import io.reactivex.Observable

interface MainView {

    fun onGetContent(observable: Observable<MutableList<Content>>)

    fun onContentLoaded()

    fun onLogout()
}