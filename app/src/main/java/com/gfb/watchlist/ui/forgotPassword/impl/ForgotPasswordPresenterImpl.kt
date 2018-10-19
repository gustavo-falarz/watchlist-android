package com.gfb.watchlist.ui.forgotPassword.impl

import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.entity.dto.UserDTO
import com.gfb.watchlist.service.UserService
import com.gfb.watchlist.ui.forgotPassword.ForgotPasswordPresenter
import com.gfb.watchlist.ui.forgotPassword.ForgotPasswordView

class ForgotPasswordPresenterImpl(val view: ForgotPasswordView) : ForgotPasswordPresenter {
    override fun resetPassword(email: String) {
        if (email.isEmpty()) {
            view.onFieldsEmpty()
        } else {
            view.onResetPassword(UserService.forgotPassword(UserDTO(email)))
        }
    }

    override fun onPasswordReset(result: Result) {
        view.onPasswordReset(result)
    }
}