package com.gfb.watchlist.ui.login.impl

import com.gfb.watchlist.entity.User
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.entity.dto.UserDTO
import com.gfb.watchlist.service.UserService
import com.gfb.watchlist.ui.login.LoginPresenter
import com.gfb.watchlist.ui.login.LoginView
import io.reactivex.Observable

class LoginPresenterImpl(val view: LoginView) : LoginPresenter {
    override fun changePassword(user: User) {
        view.changePassword(user)
    }

    override fun forgotPassword() {
        view.forgotPassword()
    }

    override fun signIn(email: String, password: String) {
        val user = UserDTO(email, password)
        view.onUserValidated(UserService.signIn(user), false)
    }

    override fun googleSignIn(email: String) {
        val user = UserDTO(email)
        view.onUserValidated(UserService.googleSignIn(user), true)
    }

    fun saveUserLocally(user: User, google: Boolean) {
        UserInfo.saveUserLocally(user, google)
        view.onUserSaved()
    }
}