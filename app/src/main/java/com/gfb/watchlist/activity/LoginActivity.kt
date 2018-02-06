package com.gfb.watchlist.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.gfb.watchlist.R
import com.gfb.watchlist.entity.User
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.entity.dto.UserDTO
import com.gfb.watchlist.service.UserService
import com.gfb.watchlist.util.Constants
import com.gfb.watchlist.util.Constants.USER_STATUS_PENDING
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import java.util.*


class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btSignUp.setOnClickListener { startActivity<NewUserActivity>() }
        btSignIn.setOnClickListener { signIn() }
        btSignInWithGoogle.setOnClickListener { googleSignIn() }
    }

    private fun signIn() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val user = UserDTO(email, password)
        when {
            !checkEmpty() -> {
                showProgress()
                UserService.validateUser(user).applySchedulers()
                        .subscribe(
                                {
                                    UserInfo.saveUserLocally(it)
                                    closeProgress()
                                    nextActivity(it)
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

    private fun checkEmpty(): Boolean {
        return etEmail.text.isNullOrEmpty() || etPassword.text.isNullOrEmpty()
    }

    private fun googleSignIn() {
        val providers = Arrays.asList(
                AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build())

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        .build(),
                Constants.RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            Constants.RC_SIGN_IN -> {
                val response = IdpResponse.fromResultIntent(data)

                when (resultCode) {
                    Activity.RESULT_OK -> {
                        val user = FirebaseAuth.getInstance().currentUser
                        if (user != null) {
                            onUserValidated(user.email)
                        }
                    }
                    else -> showWarning(response.toString())
                }
            }
        }
    }

    private fun onUserValidated(email: String?) {
        val user = UserDTO(email)
        showProgress()
        UserService.googleSignIn(user).applySchedulers()
                .subscribe(
                        {
                            closeProgress()
                            nextActivity(it)
                        },
                        { error ->
                            handleException(error)
                            closeProgress()
                        }
                )
    }

    private fun nextActivity(user: User) {
        when (USER_STATUS_PENDING) {
            user.status -> {
                warnUser(user)
            }
            else -> {
                UserInfo.saveUserLocally(user)
                startActivity<MainActivity>()
                finish()
            }
        }
    }

    private fun warnUser(user: User) {
        alert(getString(R.string.message_activate_acc), getString(R.string.title_pending_activation)) {
            positiveButton(R.string.yes) {
                val intent = Intent(baseContext, ChangePasswordActivity::class.java)
                intent.putExtra(Constants.TRANSITION_KEY_CONTENT, user)
                startActivity(intent)
            }
            negativeButton(R.string.no) {}
        }.show()
    }
}
