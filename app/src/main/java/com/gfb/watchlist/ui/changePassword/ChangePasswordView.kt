package com.gfb.watchlist.ui.changePassword

import com.gfb.watchlist.entity.User
import io.reactivex.Observable

interface ChangePasswordView {

    fun onChangePassword(observable: Observable<User>)

    fun onPasswordChanged(user: User)

    fun onPasswordsMatch()

    fun onPasswordsDontMatch()

    fun onFieldsEmpty()

}