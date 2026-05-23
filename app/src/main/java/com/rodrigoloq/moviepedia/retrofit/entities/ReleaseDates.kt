package com.rodrigoloq.moviepedia.retrofit.entities

data class ReleaseDates(val certification: String = "",
    val descriptors: List<String> = listOf(),
    val iso_639_1: String = "",
    val note: String = "",
    val release_date: String = "",
    val type: Int = 0)
