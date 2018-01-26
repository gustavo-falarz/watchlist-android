@file:Suppress("DEPRECATION")

package com.gfb.watchlist.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.gfb.watchlist.R
import com.gfb.watchlist.util.MyDatabaseOpenHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.alert
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.yesButton

/**
 * Created by Gustavo on 12/4/2017.
 */

@Suppress("DEPRECATION")
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    private lateinit var progress: ProgressDialog

    fun <T> Observable<T>.applySchedulers(): Observable<T> {
        return subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    }

    fun setupToolbar(title: Int) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(title)
        setSupportActionBar(toolbar)
    }

    fun setupActionBar() {
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.setHomeButtonEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun handleException(exception: Throwable) {
        exception.message?.let { alert(it, getString(R.string.error_title)) { yesButton { } }.show() }
    }

    fun showProgress() {
        progress = indeterminateProgressDialog(message = getString(R.string.message_loading), title = getString(R.string.title_loading))
    }

    fun closeProgress() {
        progress.hide()
    }

    fun showWarning(message: Int) {
       showWarning(getString(message))
    }

    fun showWarning(message: String) {
        alert(message, getString(R.string.error_title)) { yesButton { } }.show()
    }
    fun showMessage(message: String) {
        alert(message, getString(R.string.title_success)) { yesButton { } }.show()
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    val Context.database: MyDatabaseOpenHelper
        get() = MyDatabaseOpenHelper.getInstance(applicationContext)
}