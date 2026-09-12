package com.rodrigoloq.moviepedia.home.model

import android.util.Log
import com.rodrigoloq.moviepedia.retrofit.MovieService
import com.rodrigoloq.moviepedia.retrofit.entities.MovieResponse
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.UserDAO
import com.rodrigoloq.moviepedia.room.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val movieService: MovieService,
    private val userDAO: UserDAO
) {
    suspend fun getListLastMovies(onResult:(MovieResponse?) -> Unit) =
        withContext(Dispatchers.IO){
        try {
            val response = movieService.getListLastMovies()
            onResult(response)
        } catch (e: Exception) {
            onResult(null)
            Log.e("RLTAG", "getListLastMovies: ${e.message}", )
        }
    }

    suspend fun getListPopularMovies(onResult:(MovieResponse?) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val response = movieService.getListPopularMovies()
                onResult(response)
            } catch (e: Exception) {
                onResult(null)
                Log.e("RLTAG", "getListPopularMovies: ${e.message}", )
            }
        }

    suspend fun getUserInfo(id: Long, onResult: (User?) -> Unit) =
        withContext(Dispatchers.IO){
            val user = userDAO.getUserById(id)
            onResult(user)
        }
}