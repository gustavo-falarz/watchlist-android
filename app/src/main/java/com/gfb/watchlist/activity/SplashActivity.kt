package com.gfb.watchlist.activity

import android.os.Bundle
import android.os.Handler
import com.gfb.watchlist.R
import com.gfb.watchlist.prefs
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({
            checkStart()
        }, 2100)
    }

    private fun checkStart() {
        if (prefs.userId == "") {
            startActivity(intentFor<LoginActivity>().clearTask().newTask())
        } else {
            startActivity(intentFor<MainActivity>().clearTask().newTask())
        }
        finish()
    }

}
