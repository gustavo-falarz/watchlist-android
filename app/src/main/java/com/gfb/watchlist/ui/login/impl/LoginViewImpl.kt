package com.gfb.watchlist.ui.login.impl

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.gfb.watchlist.R
import com.gfb.watchlist.ui.BaseView
import com.gfb.watchlist.entity.User
import com.gfb.watchlist.ui.changePassword.impl.ChangePasswordViewImpl
import com.gfb.watchlist.ui.forgotPassword.impl.ForgotPasswordViewImpl
import com.gfb.watchlist.ui.login.LoginView
import com.gfb.watchlist.ui.main.impl.MainViewImpl
import com.gfb.watchlist.ui.newUser.impl.NewUserViewImpl
import com.gfb.watchlist.util.Constants
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import java.util.*


class LoginViewImpl : BaseView(), LoginView {
    var presenter = LoginPresenterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btSignUp.setOnClickListener { startActivity<NewUserViewImpl>() }
        etPassword.setOnEditorActionListener { _, _, _ ->
            signIn()
            true
        }
        btSignIn.setOnClickListener { signIn() }
        btSignInWithGoogle.setOnClickListener { googleSignIn() }
        btForgotPassword.setOnClickListener { presenter.forgotPassword() }
    }

    private fun signIn() {
        val email = etEmail.text.toString().trim().toLowerCase()
        val password = etPassword.text.toString().trim()
        when {
            !checkEmpty() -> {
                presenter.signIn(email, password)
            }
            else -> showWarning(R.string.warning_empty_fields)
        }
    }

    override fun signIn(observable: Observable<User>) {
        observable.applySchedulers()
                .subscribeBy(
                        onNext = { presenter.onUserValidated(it, false) },
                        onError = { handleException(it) },
                        onComplete = { closeProgress() }
                )
    }

    private fun checkEmpty(): Boolean {
        return etEmail.text.isNullOrEmpty() || etPassword.text.isNullOrEmpty()
    }

    private fun googleSignIn() {
        showProgress()
        val providers = Arrays.asList(
                AuthUI.IdpConfig.GoogleBuilder().build())

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        .build(),
                Constants.RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constants.RC_SIGN_IN -> {
                val response = IdpResponse.fromResultIntent(data)
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        val user = FirebaseAuth.getInstance().currentUser
                        if (user != null) {
                            googleSignIn(user.email!!)
                        }
                    }
                    else -> {
                        showWarning(response.toString())
                    }
                }
            }
        }
        closeProgress()
    }

    private fun googleSignIn(email: String) {
        presenter.googleSignIn(email)
    }

    override fun onValidateUser(observable: Observable<User>, google: Boolean) {
        showProgress()
        observable.applySchedulers()
                .subscribeBy(
                        onNext = {
                            presenter.onUserValidated(it, google)
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

    override fun onUserValidated(user: User, google: Boolean) {
        presenter.saveUserLocally(user, google)
    }

    override fun onUserSaved() {
        startActivity<MainViewImpl>()
        finish()
    }

    override fun onUserStatusPendingReset(user: User) {
        warnUser(user, getString(R.string.message_activate_acc))
    }

    override fun onUserStatusPending(user: User) {
        warnUser(user, getString(R.string.message_pending_reset))
    }

    private fun warnUser(user: User, message: String) {
        alert(message, getString(R.string.title_pending_activation)) {
            positiveButton(R.string.ok) {
                presenter.changePassword(user)
            }
            negativeButton(R.string.cancel) {}
        }.show()
    }

    override fun changePassword(user: User) {
        startActivity<ChangePasswordViewImpl>(Constants.TRANSITION_KEY_EMAIL to user.email)
        finish()
    }

    override fun forgotPassword() {
        startActivity<ForgotPasswordViewImpl>()
    }
}
