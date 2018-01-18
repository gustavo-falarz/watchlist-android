package com.gfb.watchlist.service

import com.gfb.watchlist.entity.User
import com.gfb.watchlist.entity.dto.UserDTO
import com.gfb.watchlist.service.endpoint.UserEndpoint
import io.reactivex.Observable


/**
 * Created by Gustavo on 12/26/2017.
 */
object UserService {

    fun validateUser(user: UserDTO): Observable<User> = getService().validateUser(user)

    fun addUser(user: UserDTO): Observable<User> = getService().addUser(user)

    private fun getService(): UserEndpoint = Service.createService(UserEndpoint::class.java)

}