package com.gfb.watchlist.ui.newUser.impl

import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.entity.dto.UserDTO
import com.gfb.watchlist.service.UserService
import com.gfb.watchlist.ui.newUser.NewUserPresenter
import com.gfb.watchlist.ui.newUser.NewUserView

class NewUserPresenterImpl(val view: NewUserView) : NewUserPresenter {
    override fun onUserAdded(result: Result) {
        view.onUserAdded(result)
    }

    override fun addUser(email: String) {
        view.onAddUser(UserService.addUser(UserDTO(email)))
    }
}