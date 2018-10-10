package com.gfb.watchlist.service

import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.entity.User
import com.gfb.watchlist.entity.dto.UserDTO
import com.gfb.watchlist.service.endpoint.UserEndpoint
import io.reactivex.Observable


/**
 * Created by Gustavo on 12/26/2017.
 */
object UserService {

    fun addUser(user: UserDTO): Observable<Result> = getService().addUser(user)

    fun signIn(user: UserDTO): Observable<User> = getService().signIn(user)

    fun changePassword(user: UserDTO): Observable<User> = getService().changePassword(user)

    fun forgotPassword(user: UserDTO): Observable<Result> = getService().forgotPassword(user)

    fun googleSignIn(user: UserDTO): Observable<User> = getService().googleSignIn(user)

    private fun getService(): UserEndpoint = Service.createService(UserEndpoint::class.java)

}