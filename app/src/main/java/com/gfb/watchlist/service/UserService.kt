package com.gfb.watchlist.service

import com.gfb.watchlist.entity.User
import com.gfb.watchlist.service.endpoint.UserEndpoint
import io.reactivex.Observable


/**
 * Created by Gustavo on 12/26/2017.
 */
object UserService {

    fun addUser(email: String): Observable<User> = getService().addUser(email)

    private fun getService(): UserEndpoint = Service.createService(UserEndpoint::class.java)

}