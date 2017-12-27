package com.gfb.watchlist.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by Gustavo on 10/26/2017.
 */
class Content(
        @SerializedName("id")
        var id: String,

        @SerializedName("Title")
        var title: String,

        @SerializedName("Year")
        var year: String,

        @SerializedName("Type")
        var type: String,

        @SerializedName("Poster")
        var poster: String,

        @SerializedName("Genre")
        var genre: String,

        @SerializedName("Director")
        var director: String,

        @SerializedName("Plot")
        var plot: String,

        @SerializedName("imdbID")
        var imdbID: String)
