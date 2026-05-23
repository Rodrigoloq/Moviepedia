package com.rodrigoloq.moviepedia.retrofit.entities

data class MovieReleasesResponse(val id: Int = 0,
    val results: List<MovieRelease> = listOf())
