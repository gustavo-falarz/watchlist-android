package com.gfb.watchlist.activity

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.gfb.watchlist.R
import io.reactivex.Observable

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Gustavo on 12/4/2017.
 */

open class BaseActivity : AppCompatActivity() {


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


}