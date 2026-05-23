package com.rodrigoloq.moviepedia.retrofit.entities

data class CreditsResponse(val id: Int = 0,
    val cast: List<Cast> = listOf(),
    val crew: List<Crew> = listOf()
    ){

}
