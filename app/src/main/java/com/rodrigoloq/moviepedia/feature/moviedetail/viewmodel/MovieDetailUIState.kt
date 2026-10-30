package com.rodrigoloq.moviepedia.feature.moviedetail.viewmodel

import com.rodrigoloq.moviepedia.data.retrofit.entities.AlternativeTitles
import com.rodrigoloq.moviepedia.data.retrofit.entities.CreditsResponse
import com.rodrigoloq.moviepedia.data.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.data.retrofit.entities.MovieReleasesResponse

data class MovieDetailUIState(
    val movieData: MovieDetailResponse = MovieDetailResponse(),
    val alternativeTitles: List<AlternativeTitles> = listOf(),
    val credits: CreditsResponse = CreditsResponse(),
    val releases: MovieReleasesResponse = MovieReleasesResponse(),
    val isFavorite: Boolean = false,
    val inProgress: Boolean = false
)
