package com.gfb.watchlist.ui.login

import com.gfb.watchlist.entity.User
import io.reactivex.Observable

interface LoginView {

    fun onValidateUser(observable: Observable<User>, google: Boolean)

    fun onUserValidated(user: User, google: Boolean)

    fun onUserStatusPending(user: User)

    fun onUserStatusPendingReset(user: User)

    fun onUserSaved()

    fun forgotPassword()

    fun changePassword(user: User)

    fun onEmptyFields()

}