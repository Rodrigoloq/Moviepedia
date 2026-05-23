package com.rodrigoloq.moviepedia.retrofit.entities

object GenreMapper {
    private val genresMap = mapOf(
        0 to "Género",
        28 to "Acción",
        12 to "Aventura",
        16 to "Animación",
        35 to "Comedia",
        80 to "Crimen",
        99 to "Documental",
        18 to "Drama",
        10751 to "Familia",
        14 to "Fantasía",
        36 to "Historia",
        27 to "Terror",
        10402 to "Música",
        9648 to "Misterio",
        10749 to "Romance",
        878 to "Ciencia ficción",
        10770 to "Película de TV",
        53 to "Suspense",
        10752 to "Bélica",
        37 to "Western"
    )

    fun getGenreNames(ids: List<Int>?): List<String> {
        return ids!!.mapNotNull { genresMap[it] }
    }
}