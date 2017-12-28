package com.gfb.watchlist.activity

import android.os.Bundle
import com.gfb.watchlist.R
import com.gfb.watchlist.entity.User
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.service.UserService
import kotlinx.android.synthetic.main.activity_new_user.*
import org.jetbrains.anko.startActivity

class NewUserActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)
        btEnter.setOnClickListener({ addUser() })

    }

    private fun addUser() {
        var email = etEmail.text.toString().trim()
        when {
            email != null -> {
                showProgress()
                UserService.addUser(email).applySchedulers()
                        .subscribe(
                                { user ->
                                    saveUserLocally(user)
                                    closeProgress()
                                },
                                { error ->
                                    handleException(error)
                                    closeProgress()
                                }
                        )

            }
            else -> showWarning("Inform an email so you won't lose your data")
        }
    }

    private fun saveUserLocally(user: User) {
        UserInfo.userId = user.id
        UserInfo.email = user.email
        startActivity<MainActivity>()
    }
}