package com.gfb.watchlist.ui.forgotPassword

import com.gfb.watchlist.entity.Result
import io.reactivex.Observable

interface ForgotPasswordView {

    fun onPasswordReset(result: Result)

    fun onResetPassword(observable: Observable<Result>)

    fun onFieldsEmpty()

}