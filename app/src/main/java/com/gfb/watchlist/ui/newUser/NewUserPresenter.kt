package com.gfb.watchlist.ui.newUser

import com.gfb.watchlist.entity.Result

interface NewUserPresenter {

    fun addUser(email: String)

    fun onUserAdded(result: Result)
}