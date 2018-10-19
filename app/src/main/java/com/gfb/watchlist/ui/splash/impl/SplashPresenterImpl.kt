package com.gfb.watchlist.ui.splash.impl

import android.os.Handler
import com.gfb.watchlist.ui.splash.SplashPresenter
import com.gfb.watchlist.ui.splash.SplashView

class SplashPresenterImpl(val view: SplashView) : SplashPresenter {
    override fun waitStart() {
        Handler().postDelayed({
            view.onFinishLoading()
        }, 2100)
    }

    override fun showNext(userId: String) {
        if (userId == "") {
            view.showLogin()
        } else {
            view.showMain()
        }
    }
}