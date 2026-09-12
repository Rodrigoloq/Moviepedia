package com.rodrigoloq.moviepedia.moviedetail.viewmodel

import com.rodrigoloq.moviepedia.retrofit.entities.AlternativeTitles
import com.rodrigoloq.moviepedia.retrofit.entities.CreditsResponse
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.retrofit.entities.MovieReleasesResponse

data class MovieDetailUIState(
    val movieData: MovieDetailResponse = MovieDetailResponse(),
    val alternativeTitles: List<AlternativeTitles> = listOf(),
    val credits: CreditsResponse = CreditsResponse(),
    val releases: MovieReleasesResponse = MovieReleasesResponse(),
    val isFavorite: Boolean = false,
    val inProgress: Boolean = false
)
