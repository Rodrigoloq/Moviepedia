package com.rodrigoloq.moviepedia.feature.account.viewmodel

import androidx.navigation.navDeepLink
import com.rodrigoloq.moviepedia.data.room.entities.User

data class AccountUIState(
    val user: User = User(username = "", email = "", password = ""),
    var inProgress: Boolean = false,
    val errorLoadingUser: Boolean = false,
    val successUpdatingUser: Boolean? = null,
)
