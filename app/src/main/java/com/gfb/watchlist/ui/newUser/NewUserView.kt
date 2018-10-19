package com.gfb.watchlist.ui.newUser

import com.gfb.watchlist.entity.Result
import io.reactivex.Observable

interface NewUserView {

    fun onUserAdded(result: Result)

    fun onAddUser(observable: Observable<Result>)
}