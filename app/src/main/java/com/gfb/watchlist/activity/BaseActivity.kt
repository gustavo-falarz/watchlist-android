package com.gfb.watchlist.activity

import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ProgressBar
import com.gfb.watchlist.R
import com.gfb.watchlist.util.MyDatabaseOpenHelper
import io.reactivex.Observable

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.alert
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.progressDialog
import org.jetbrains.anko.yesButton

/**
 * Created by Gustavo on 12/4/2017.
 */

open class BaseActivity : AppCompatActivity() {

    lateinit var progress: ProgressDialog

    fun <T> Observable<T>.applySchedulers(): Observable<T> {
        return subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    }

    fun setupToolbar(title: Int) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(title)
        setSupportActionBar(toolbar)

    }

    fun setuActionBar() {
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
        progress = indeterminateProgressDialog(message = "Please wait a bit…", title = "Fetching data")
    }

    fun closeProgress() {
        progress.hide()
    }

    fun showWarning(message: String){
        alert (message, getString(R.string.error_title)){yesButton {  }}
    }
    val Context.database: MyDatabaseOpenHelper
        get() = MyDatabaseOpenHelper.getInstance(applicationContext)
}