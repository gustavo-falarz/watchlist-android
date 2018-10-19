package com.gfb.watchlist.ui.changePassword.impl

import com.gfb.watchlist.entity.User
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.entity.dto.UserDTO
import com.gfb.watchlist.service.UserService
import com.gfb.watchlist.ui.changePassword.ChangePasswordPresenter
import com.gfb.watchlist.ui.changePassword.ChangePasswordView

class ChangePasswordPresenterImpl(val view: ChangePasswordView) : ChangePasswordPresenter {
    override fun onPasswordChanged(user: User) {
        UserInfo.saveUserLocally(user, false)
        view.onPasswordChanged(user)
    }

    override fun checkPasswords(pass1: String, pass2: String) {
        when{
            pass1.isEmpty() || pass2.isEmpty() -> view.onFieldsEmpty()
            pass1 != pass2 -> view.onPasswordsDontMatch()
            else -> view.onPasswordsMatch()
        }
    }

    override fun changePassword(email: String, password: String) {
        view.onChangePassword(UserService.changePassword(UserDTO(email, password)))
    }
}