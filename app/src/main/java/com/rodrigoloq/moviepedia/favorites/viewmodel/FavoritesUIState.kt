package com.rodrigoloq.moviepedia.favorites.viewmodel

import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse

data class FavoritesUIState(val favorites: List<MovieDetailResponse> = listOf(),
    val inProgress: Boolean = false,
    val isMovieReviewed: Boolean = false,
    val successRemovingFavorite: Boolean? = null)
