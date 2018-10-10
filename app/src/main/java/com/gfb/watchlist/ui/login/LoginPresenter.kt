package com.gfb.watchlist.ui.login

import com.gfb.watchlist.entity.User
import io.reactivex.Observable

interface LoginPresenter {

    fun signIn(email: String, password: String)

    fun googleSignIn(email: String)
}