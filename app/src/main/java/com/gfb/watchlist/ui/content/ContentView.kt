package com.gfb.watchlist.ui.content

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result

interface ContentView {

    fun archiveContent(content: Content)

    fun deleteContent(result: Result, content: Content)

}