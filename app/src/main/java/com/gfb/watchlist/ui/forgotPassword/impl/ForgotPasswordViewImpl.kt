package com.gfb.watchlist.ui.forgotPassword.impl

import android.os.Bundle
import com.gfb.watchlist.R
import com.gfb.watchlist.ui.BaseView
import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.ui.forgotPassword.ForgotPasswordView
import com.gfb.watchlist.ui.login.impl.LoginViewImpl
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_forgot_password.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class ForgotPasswordViewImpl : BaseView(), ForgotPasswordView {
       val presenter = ForgotPasswordPresenterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        setupActionBar()
        btReset.setOnClickListener { resetPassword() }
        btCancel.setOnClickListener { onBackPressed() }
        etEmail.setOnEditorActionListener { _, _, _ ->
            resetPassword()
            true
        }
    }

    private fun resetPassword() {
        presenter.resetPassword(etEmail.text.toString())
    }

    override fun onResetPassword(observable: Observable<Result>) {
        showProgress()
        observable.applySchedulers()
                .subscribeBy(
                        onNext = {
                            presenter.onPasswordReset(it)
                        },
                        onError = { error ->
                            handleException(error)
                            closeProgress()
                        },
                        onComplete = {
                            closeProgress()
                        }
                )
    }

    override fun onFieldsEmpty() {
        showWarning(R.string.warning_empty_fields)
    }

    override fun onPasswordReset(result: Result) {
        alert(result.message, getString(R.string.title_success)) {
            yesButton {
                startActivity<LoginViewImpl>()
                finish()
            }
            noButton {  }
        }.show()
    }

}
