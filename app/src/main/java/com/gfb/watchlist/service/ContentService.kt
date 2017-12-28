package com.gfb.watchlist.service

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.service.endpoint.MovieEndpoint
import io.reactivex.Observable


/**
 * Created by Gustavo on 10/26/2017.
 */

object ContentService {


    fun addContent(dto: UserContentDTO): Observable<Result> {
        return getService().addContent(dto)
    }

    fun archiveContent(dto: UserContentDTO): Observable<Result> {
        return getService().archiveContent(dto)
    }
    fun findArchive(userId: String): Observable<List<Content>> {
        return getService().findArchive(userId)
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
