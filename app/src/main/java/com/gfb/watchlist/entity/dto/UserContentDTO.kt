package com.gfb.watchlist.entity.dto

import com.gfb.watchlist.entity.Content


class UserContentDTO(var userId: String?,
                     var content: Content?,
                     var type: Type?) {

    constructor(userId: String?) : this(userId, null, null)

    constructor(userId: String?, content: Content?) : this(userId, content, null)

    enum class Type {
        MOVIE,
        SERIES
    }
}