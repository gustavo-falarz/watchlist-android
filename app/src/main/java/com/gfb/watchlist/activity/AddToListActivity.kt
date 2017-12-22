package com.gfb.watchlist.activity

import android.os.Bundle
import com.gfb.watchlist.R

class AddToListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_list)
        setupToolbar(R.string.search_menu_title)
        setuActionBar()
    }
}
