package com.rodrigoloq.moviepedia.data.retrofit.entities

data class CreditsResponse(val id: Int = 0,
    val cast: List<Cast> = listOf(),
    val crew: List<Crew> = listOf()
    ){

}
