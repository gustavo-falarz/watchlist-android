package com.gfb.watchlist.service

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Response
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.service.endpoint.MovieEndpoint
import io.reactivex.Observable


/**
 * Created by Gustavo on 10/26/2017.
 */

object ContentService {


    fun addContent(dto: UserContentDTO): Observable<Response> {
        return getService().addContent(dto)
    }

    fun findContent(userId: String): Observable<List<Content>> {
        return getService().findContent(userId)
    }

    fun findContent(dto: UserContentDTO): Observable<List<Content>> {
        return getService().findContent(dto)
    }

    fun searchOnImdb(param: String): Observable<List<Content>>{
        return getService().searchOnImdb(param)
    }

    private fun getService(): MovieEndpoint {
        return Service.createService(MovieEndpoint::class.java)
    }
}
