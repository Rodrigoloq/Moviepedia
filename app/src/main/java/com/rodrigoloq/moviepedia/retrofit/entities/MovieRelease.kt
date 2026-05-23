package com.rodrigoloq.moviepedia.retrofit.entities

data class MovieRelease(val iso_3166_1: String = "",
    val release_dates: List<ReleaseDates> = listOf())
