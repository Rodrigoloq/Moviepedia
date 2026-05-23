package com.rodrigoloq.moviepedia.search.model

import android.util.Log
import com.rodrigoloq.moviepedia.retrofit.MovieService
import com.rodrigoloq.moviepedia.retrofit.entities.MovieResponse
import com.rodrigoloq.moviepedia.retrofit.entities.Result
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepository {

    private val movieService: MovieService by lazy { MoviepediaApp.rdb.create(MovieService::class.java)}

    suspend fun getListSearchResult(movie: String, onResult:(MovieResponse?) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val response = movieService.getListSearchResult(movie = movie)
                onResult(response)
            } catch (e: Exception) {
                onResult(null)
            }
        }

    suspend fun getListTopRatedMovies(onResult:(List<Result>?) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                var result: List<Result> = movieService.getListTopRatedMovies().results
                for(i in 2..3){
                    result = result + movieService.getListTopRatedMovies(page = i).results
                }
                onResult(result)
            } catch (e: Exception) {
                onResult(null)
            }
        }
}