package com.gfb.watchlist.entity.dto

import com.gfb.watchlist.entity.Content

/**
 * Created by Gustavo on 18/01/2018.
 */
class UserDTO(var id: String?,
              var email: String?,
              var password: String?,
              var watchList: List<Content>?,
              var archive: List<Content>?) {


    constructor(email: String, password: String) : this(null, email, password, null, null)

    constructor(email: String, password: String, watchList: MutableList<Content>
                , archive: List<Content>?) : this(null, email, password, watchList, archive)
}