package com.gfb.watchlist.ui.contentDetails

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result

interface ContentDetailsPresenter {

    fun archiveContent(userId: String, content: Content)

    fun deleteContent(userId: String, content: Content)

    fun onContentArchived(result: Result)

    fun onContentDeleted(result: Result)

    fun shareContent(imdbId: String)

    fun populateFields()

}