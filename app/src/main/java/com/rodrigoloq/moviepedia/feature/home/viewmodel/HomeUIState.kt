package com.rodrigoloq.moviepedia.feature.home.viewmodel

import com.rodrigoloq.moviepedia.retrofit.entities.Result
import com.rodrigoloq.moviepedia.room.entities.User

data class HomeUIState(
    val lastMovies: List<Result> = listOf(),
    val popularMovies: List<Result> = listOf(),
    val userData: User = User(
        username = "",
        email = "",
        password = ""
    ),
    val inProgress: Boolean = false,
)
