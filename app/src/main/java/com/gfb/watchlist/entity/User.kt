package com.gfb.watchlist.entity

/**
 * Created by Gustavo on 12/26/2017.
 */
class User(
        var id: String?,
        var email: String?,
        var password: String?,
        var watchList: List<Content>?,
        var archive: List<Content>?) {

    constructor(email: String, password: String) :this(null, email, password, null, null)
    constructor(email: String, password: String, watchList: MutableList<Content>
                , archive: List<Content>?) :this(null, email, password, watchList, archive)

    constructor(id: String):this(id, null, null, null, null)
}

