package com.gfb.watchlist.ui.forgotPassword

import com.gfb.watchlist.entity.Result

interface ForgotPasswordPresenter {

    fun resetPassword(email: String)

    fun onPasswordReset(result: Result)

}