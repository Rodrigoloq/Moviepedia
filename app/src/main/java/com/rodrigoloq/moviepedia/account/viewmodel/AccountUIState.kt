package com.rodrigoloq.moviepedia.account.viewmodel

import androidx.navigation.navDeepLink
import com.rodrigoloq.moviepedia.room.entities.User

data class AccountUIState(
    val user: User = User(username = "", email = "", password = ""),
    var inProgress: Boolean = false,
    val errorLoadingUser: Boolean = false,
    val successUpdatingUser: Boolean? = null,
)
