package com.gfb.watchlist.activity

import android.os.Bundle
import com.gfb.watchlist.R
import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.entity.dto.UserDTO
import com.gfb.watchlist.service.UserService
import kotlinx.android.synthetic.main.activity_forgot_password.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class ForgotPasswordActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        btReset.setOnClickListener({ resetPassword() })
        btCancel.setOnClickListener({ onBackPressed() })
        etEmail.setOnEditorActionListener { _, _, _ ->
            resetPassword()
            true
        }
    }


    private fun resetPassword() {
        val email = etEmail.text.toString().trim().toLowerCase()
        val user = UserDTO(email)
        when {
            !checkEmpty() -> {
                showProgress()
                UserService.forgotPassword(user).applySchedulers()
                        .subscribe(
                                {
                                    closeProgress()
                                    warnUser(it)
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


    private fun warnUser(result: Result) {
        alert(result.message, getString(R.string.title_success)) {
            yesButton {
                startActivity<LoginActivity>()
                finish()
            }
        }.show()
    }

    private fun checkEmpty(): Boolean {
        return etEmail.text.isNullOrEmpty()
    }
}
