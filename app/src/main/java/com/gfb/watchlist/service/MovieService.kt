package com.gfb.watchlist.service

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.service.endpoint.MovieEndpoint
import io.reactivex.Observable


/**
 * Created by Gustavo on 10/26/2017.
 */

object MovieService {


    fun addMovie(movie: Content): Observable<Content> {
        return getService().addMovie(movie)
    }

    fun searchOnImdb(param: String): Observable<List<Content>>{
        return getService().searchOnImdb(param)
    }

    private fun getService(): MovieEndpoint {
        return Service.createService(MovieEndpoint::class.java)
    }
}
