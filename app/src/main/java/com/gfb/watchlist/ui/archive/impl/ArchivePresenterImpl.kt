package com.gfb.watchlist.ui.archive.impl

import com.gfb.watchlist.service.ContentService
import com.gfb.watchlist.ui.archive.ArchivePresenter
import com.gfb.watchlist.ui.archive.ArchiveView

class ArchivePresenterImpl(val view: ArchiveView) : ArchivePresenter {

    override fun getArchive(userId: String) {
        view.getArchive(ContentService.findArchive(userId))
    }

    override fun clearArchive(userId: String) {
        view.clearArchive(ContentService.clearArchive(userId))
    }

}
