package com.gfb.watchlist.ui.changePassword

import com.gfb.watchlist.entity.User

interface ChangePasswordPresenter {

    fun checkPasswords(pass1: String, pass2: String)

    fun changePassword(email: String, password: String)

    fun onPasswordChanged(user: User)

}