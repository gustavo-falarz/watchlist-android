package com.gfb.watchlist.ui.login

interface LoginPresenter {

    fun signIn(email: String, password: String)

    fun googleSignIn(email: String)

    fun forgotPassword()
}