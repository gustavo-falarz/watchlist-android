package com.gfb.watchlist.activity

import android.os.Bundle
import com.gfb.watchlist.R
import com.gfb.watchlist.entity.UserInfo
import org.jetbrains.anko.startActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        if (UserInfo.userId == "") {
            startActivity<NewUserActivity>()
        } else {
            startActivity<MainActivity>()
        }
    }


}
