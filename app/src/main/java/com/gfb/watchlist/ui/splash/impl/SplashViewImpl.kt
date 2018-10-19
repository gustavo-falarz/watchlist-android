package com.gfb.watchlist.ui.splash.impl

import android.os.Bundle
import com.gfb.watchlist.R
import com.gfb.watchlist.ui.BaseView
import com.gfb.watchlist.prefs
import com.gfb.watchlist.ui.login.impl.LoginViewImpl
import com.gfb.watchlist.ui.main.impl.MainViewImpl
import com.gfb.watchlist.ui.splash.SplashView
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask


class SplashViewImpl : BaseView(), SplashView {
    val presenter = SplashPresenterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        presenter.waitStart()
    }

    override fun onFinishLoading() {
        presenter.showNext(prefs.userId)
    }

    override fun showMain() {
        startActivity(intentFor<MainViewImpl>().clearTask().newTask())
        finish()
    }

    override fun showLogin() {
        startActivity(intentFor<LoginViewImpl>().clearTask().newTask())
        finish()
    }

}
