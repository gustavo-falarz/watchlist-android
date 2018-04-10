package com.gfb.watchlist.activity

import android.os.Bundle
import com.gfb.watchlist.R
import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.entity.dto.UserDTO
import com.gfb.watchlist.service.UserService
import kotlinx.android.synthetic.main.activity_new_user.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class NewUserActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)
        btEnter.setOnClickListener({ addUser() })
        btCancel.setOnClickListener({ onBackPressed() })
        etEmail.setOnEditorActionListener { _, _, _ ->
            addUser()
            true
        }

    }

    private fun addUser() {
        val email = etEmail.text.toString().trim().toLowerCase()
        val user = UserDTO(email)
        when {
            !checkEmpty() -> {
                showProgress()
                UserService.addUser(user).applySchedulers()
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
        alert(result.message, getString(R.string.title_account_created)) {
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