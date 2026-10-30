package com.rodrigoloq.moviepedia.feature.search.model

import android.util.Log
import com.rodrigoloq.moviepedia.data.retrofit.MovieService
import com.rodrigoloq.moviepedia.data.retrofit.entities.MovieResponse
import com.rodrigoloq.moviepedia.data.retrofit.entities.Result
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val movieService: MovieService
){
    suspend fun getListSearchResult(movie: String, onResult:(MovieResponse?) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val response = movieService.getListSearchResult(movie = movie)
                onResult(response)
            } catch (e: Exception) {
                onResult(null)
                Log.e("RLTAG", "getListSearchResult: ${e.message}", )
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
                Log.e("RLTAG", "getListTopRatedMovies: ${e.message}", )
            }
        }
}