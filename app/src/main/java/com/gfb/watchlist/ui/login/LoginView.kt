package com.gfb.watchlist.ui.login

import com.gfb.watchlist.entity.User
import io.reactivex.Observable

interface LoginView {

    fun signIn(observable: Observable<User>)


    fun onUserValidated(observable: Observable<User>)

    fun onUserSaved()
}