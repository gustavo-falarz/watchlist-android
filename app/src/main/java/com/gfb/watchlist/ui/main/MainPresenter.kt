package com.gfb.watchlist.ui.main

import com.gfb.watchlist.entity.Content

interface MainPresenter {

    fun getContent()

    fun onContentLoaded(content: MutableList<Content>)

    fun logout()


}