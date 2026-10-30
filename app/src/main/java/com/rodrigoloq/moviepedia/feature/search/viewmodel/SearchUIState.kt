package com.rodrigoloq.moviepedia.feature.search.viewmodel

import com.rodrigoloq.moviepedia.data.retrofit.entities.Result

data class SearchUIState(
    val searchResults: List<Result> = listOf(),
    val topRatedMovies: List<Result> = listOf(),
    val inProgress: Boolean = false)
