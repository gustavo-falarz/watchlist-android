package com.gfb.watchlist.activity

import android.os.Bundle
import com.gfb.watchlist.R
import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.entity.User
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.entity.dto.UserDTO
import com.gfb.watchlist.service.UserService
import com.gfb.watchlist.util.Constants
import kotlinx.android.synthetic.main.activity_change_password.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class ChangePasswordActivity : BaseActivity() {
    lateinit var email: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        setupToolbar(R.string.title_new_password)
        setupActionBar()
        email = intent.getSerializableExtra(Constants.TRANSITION_KEY_CONTENT) as String

        btChangePassword.setOnClickListener { prepare() }
    }

    private fun prepare() {
        when {
            checkEmpty() -> {
                showWarning(R.string.warning_empty_fields)
            }
            passwordsMatch() -> {
                showWarning(R.string.warning_passwords_do_not_match)
            }
            else -> {
                changePassword()
            }
        }
    }

    private fun checkEmpty(): Boolean {
        return etPassword.text.isNullOrEmpty() || etConfirmation.text.isNullOrEmpty()
    }

    private fun passwordsMatch(): Boolean {
        return etPassword.text == etPassword.text
    }

    private fun changePassword() {
        showProgress()
        val email = email
        val user = UserDTO(email, etConfirmation.text.toString())
        UserService.changePassword(user).applySchedulers()
                .subscribe(
                        {
                            closeProgress()
                            handleResult(it)
                        },
                        { error ->
                            handleException(error)
                            closeProgress()
                        }
                )
    }

    private fun handleResult(user: User) {
        alert(getString(R.string.message_password_changed), getString(R.string.error_title)) {
            yesButton {
                startActivity<MainActivity>()
                UserInfo.saveUserLocally(user)
                finish()
            }
        }.show()
    }

}


