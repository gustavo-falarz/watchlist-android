package com.gfb.watchlist.ui.login

import com.gfb.watchlist.entity.User

interface LoginPresenter {

    fun signIn(email: String, password: String)

    fun googleSignIn(email: String)

    fun forgotPassword()

    fun changePassword(user: User)
}