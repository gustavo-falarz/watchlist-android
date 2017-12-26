package com.gfb.watchlist

import android.app.Application
import com.chibatching.kotpref.Kotpref

/**
 * Created by Gustavo on 12/26/2017.
 */
class WatchlistApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Kotpref.init(applicationContext)

    }
}