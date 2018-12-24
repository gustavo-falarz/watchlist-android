package com.gfb.watchlist.ui.newUser.impl

import android.os.Bundle
import com.gfb.watchlist.R
import com.gfb.watchlist.ui.BaseView
import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.ui.login.impl.LoginViewImpl
import com.gfb.watchlist.ui.newUser.NewUserView
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_new_user.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class NewUserViewImpl : BaseView(), NewUserView {
    private val presenter = NewUserPresenterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)
        setupActionBar()
        btEnter.setOnClickListener { addUser() }
        btCancel.setOnClickListener { onBackPressed() }
        etEmail.setOnEditorActionListener { _, _, _ ->
            addUser()
            true
        }
    }

    override fun addUser() {
        val email = etEmail.text.toString().trim().toLowerCase()
        when {
            !checkEmpty() -> presenter.addUser(email)
            else -> showWarning(R.string.warning_empty_fields)
        }
    }

    override fun onAddUser(observable: Observable<Result>) {
        showProgress()
        observable.applySchedulers()
                .subscribeBy(
                        onNext = {
                            presenter.onUserAdded(it)
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

    override fun onUserAdded(result: Result) {
        alert(result.message, getString(R.string.title_account_created)) {
            yesButton {
                startActivity<LoginViewImpl>()
                finish()
            }
        }.show()
    }

    private fun checkEmpty(): Boolean {
        return etEmail.text.isNullOrEmpty()
    }
}