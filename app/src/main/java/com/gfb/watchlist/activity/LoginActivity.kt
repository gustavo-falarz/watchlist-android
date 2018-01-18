package com.gfb.watchlist.activity

import android.os.Bundle
import com.gfb.watchlist.R
import com.gfb.watchlist.entity.User
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.service.UserService
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btSignUp.setOnClickListener { startActivity<NewUserActivity>() }
        btSignIn.setOnClickListener({ signIn() })
    }

    private fun signIn() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val user = User(null, email, password, null, null)
        when {
            !checkEmpty() -> {
                showProgress()
                UserService.validateUser(user).applySchedulers()
                        .subscribe(
                                {
                                    saveUserLocally(it)
                                    closeProgress()
                                    startActivity<MainActivity>()
                                },
                                { error ->
                                    handleException(error)
                                    closeProgress()
                                }
                        )
            }
            else -> showWarning(R.string.warning_empty_fields)

        }
    }

    private fun saveUserLocally(user: User) {
        UserInfo.userId = user.id!!
        UserInfo.email = user.email
    }

    private fun checkEmpty(): Boolean {
        return etEmail.text.isNullOrEmpty() || etPassword.text.isNullOrEmpty()
    }
}
