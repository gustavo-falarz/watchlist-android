package com.gfb.watchlist

import android.app.Application
import com.gfb.watchlist.entity.UserInfo

/**
 * Created by Gustavo on 12/26/2017.
 */

val prefs: UserInfo by lazy {
    WatchlistApplication.prefs!!
}

class WatchlistApplication : Application() {
    companion object {
        lateinit var prefs: UserInfo
    }

    override fun onCreate() {
        super.onCreate()
        prefs = UserInfo(applicationContext)
    }
}