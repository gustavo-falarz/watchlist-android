package com.gfb.watchlist

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.google.firebase.FirebaseApp

/**
 * Created by Gustavo on 12/26/2017.
 */
class WatchlistApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Kotpref.init(applicationContext)
        FirebaseApp.initializeApp(applicationContext)
    }
}