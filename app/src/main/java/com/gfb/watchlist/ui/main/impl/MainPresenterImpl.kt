package com.gfb.watchlist.ui.main.impl

import android.content.Context
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.prefs
import com.gfb.watchlist.service.ContentService
import com.gfb.watchlist.ui.main.MainPresenter
import com.gfb.watchlist.ui.main.MainView

class MainPresenterImpl(val view: MainView, val context: Context) : MainPresenter {
    override fun addNewContent() {
        view.onCallAddNewContent()
    }

    override fun getContent() {
        view.onGetContent(ContentService.findContent(UserContentDTO(prefs.userId)))
    }

    override fun onContentLoaded(content: MutableList<Content>) {
        ContentContainer.initContent(content)
        ContentContainer.updated = false
        view.onContentLoaded()
    }

    override fun logout() {
        UserInfo.clearData(context)
        ContentContainer.updated = true
        view.onLogout()
    }


}