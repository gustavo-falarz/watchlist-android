package com.gfb.watchlist.ui.archive

interface ArchivePresenter {

    fun getArchive(userId: String)

    fun clearArchive(userId: String)

}