package com.gfb.watchlist.activity

import android.os.Bundle
import com.gfb.watchlist.R
import com.gfb.watchlist.entity.UserInfo
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
        if (UserInfo.userId == "") {
            startActivity(intentFor<LoginActivity>().clearTask().newTask())
        } else {
            startActivity(intentFor<LoginActivity>().clearTask().newTask())
        }
    }


}
