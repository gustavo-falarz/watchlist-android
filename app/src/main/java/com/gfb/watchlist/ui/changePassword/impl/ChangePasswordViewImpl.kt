package com.gfb.watchlist.ui.changePassword.impl

import android.os.Bundle
import com.gfb.watchlist.R
import com.gfb.watchlist.ui.BaseView
import com.gfb.watchlist.entity.User
import com.gfb.watchlist.ui.changePassword.ChangePasswordView
import com.gfb.watchlist.ui.main.impl.MainViewImpl
import com.gfb.watchlist.util.Constants
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_change_password.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class ChangePasswordViewImpl : BaseView(), ChangePasswordView {
    val presenter = ChangePasswordPresenterImpl(this)
    lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        (R.string.title_new_password)
        setupActionBar()
        email = intent.getStringExtra(Constants.TRANSITION_KEY_EMAIL)
        etConfirmation.setOnEditorActionListener { _, _, _ ->
            prepare()
            true
        }
        btChangePassword.setOnClickListener { prepare() }
    }

    private fun prepare() {
        presenter.checkPasswords(etPassword.text.toString(), etConfirmation.text.toString())
    }

    override fun onChangePassword(observable: Observable<User>) {
        showProgress()
        observable.applySchedulers().subscribeBy(
                onNext = {
                    presenter.onPasswordChanged(it)
                },
                onError = {
                    closeProgress()
                    handleException(it)
                },
                onComplete = {
                    closeProgress()
                }
        )
    }

    override fun onPasswordChanged(user: User) {
        alert(getString(R.string.message_password_changed), getString(R.string.title_success)) {
            yesButton {
                startActivity<MainViewImpl>()
                finish()
            }
        }.show()
    }

    override fun onPasswordsMatch() {
        presenter.changePassword(email, etConfirmation.text.toString())
    }

    override fun onPasswordsDontMatch() {
        showWarning(R.string.warning_passwords_do_not_match)
    }

    override fun onFieldsEmpty() {
        showWarning(R.string.warning_empty_fields)
    }

}


