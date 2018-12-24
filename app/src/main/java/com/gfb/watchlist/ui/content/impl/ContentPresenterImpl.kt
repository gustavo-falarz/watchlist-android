package com.gfb.watchlist.ui.content.impl

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.service.ContentService
import com.gfb.watchlist.ui.content.ContentPresenter
import com.gfb.watchlist.ui.content.ContentView
import com.gfb.watchlist.util.Constants

class ContentPresenterImpl(val view: ContentView) : ContentPresenter {

    override fun searchContent(term: String, type: String) {
        when (type) {
            (Constants.TYPE_ALL) -> {
                view.onContentSearch(ContentContainer.content.filter {
                    it.title.contains(term, true)
                })
            }
            else -> {
                view.onContentSearch(ContentContainer.getContent(type).filter {
                    it.title.contains(term, true)
                })
            }
        }
    }

    override fun showDetails(content: Content) {
        view.onShowDetails(content)
    }

    override fun archiveContent(userId: String, content: Content) {
        view.onArchiveContent(ContentService.archiveContent(UserContentDTO(userId, content)), content)
    }

    override fun deleteContent(content: Content) {
        ContentContainer.content.remove(content)
        view.onContentDeleted()
    }

    fun setupAdapter(type: String) {
        when (type) {
            (Constants.TYPE_ALL) -> {
                view.createAdapter(ContentContainer.content)
            }
            else -> {
                view.createAdapter(ContentContainer.getContent(type))
            }
        }
    }
}