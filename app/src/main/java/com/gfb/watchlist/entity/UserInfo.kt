package com.gfb.watchlist.entity

import com.chibatching.kotpref.KotprefModel

/**
 * Created by Gustavo on 12/26/2017.
 */
object UserInfo: KotprefModel() {

    var userId by stringPref()
    var email by stringPref()

}