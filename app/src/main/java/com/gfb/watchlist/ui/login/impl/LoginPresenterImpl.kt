package com.gfb.watchlist.ui.login.impl

import com.gfb.watchlist.entity.User
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.entity.dto.UserDTO
import com.gfb.watchlist.service.UserService
import com.gfb.watchlist.ui.login.LoginPresenter
import com.gfb.watchlist.ui.login.LoginView

class LoginPresenterImpl(val view: LoginView) : LoginPresenter {
    override fun onUserValidated(user: User, google: Boolean) {
        when (user.status) {
            User.Status.PENDING -> {
                view.onUserStatusPending(user)
            }
            User.Status.PENDING_RESET -> {
                view.onUserStatusPendingReset(user)
            }
            User.Status.ACTIVE -> {
                view.onUserValidated(user, google)
            }
        }
    }

    override fun changePassword(user: User) {
        view.changePassword(user)
    }

    override fun forgotPassword() {
        view.forgotPassword()
    }

    override fun signIn(email: String, password: String) {
        val user = UserDTO(email, password)
        view.onValidateUser(UserService.signIn(user), false)
    }

    override fun googleSignIn(email: String) {
        val user = UserDTO(email)
        view.onValidateUser(UserService.googleSignIn(user), true)
    }

    fun saveUserLocally(user: User, google: Boolean) {
        UserInfo.saveUserLocally(user, google)
        view.onUserSaved()
    }
}