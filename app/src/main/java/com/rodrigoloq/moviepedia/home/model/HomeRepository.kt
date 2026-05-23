package com.rodrigoloq.moviepedia.home.model

import android.util.Log
import com.rodrigoloq.moviepedia.retrofit.MovieService
import com.rodrigoloq.moviepedia.retrofit.entities.MovieResponse
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.UserDAO
import com.rodrigoloq.moviepedia.room.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository {
    private val movieService: MovieService by lazy { MoviepediaApp.rdb.create(MovieService::class.java)}
    private val userDAO: UserDAO by lazy { MoviepediaApp.ldb.userDAO() }

    suspend fun getListLastMovies(onResult:(MovieResponse?) -> Unit) =
        withContext(Dispatchers.IO){
        try {
            val response = movieService.getListLastMovies()
            onResult(response)
        } catch (e: Exception) {
            Log.e("RLTAG", "getListLastMovies: ${e.message}", )
            onResult(null)
        }
    }

    suspend fun getListPopularMovies(onResult:(MovieResponse?) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val response = movieService.getListPopularMovies()
                onResult(response)
            } catch (e: Exception) {
                Log.e("RLTAG", "getListPopularMovies: ${e.message}", )
                onResult(null)
            }
        }

    suspend fun getUserInfo(id: Long, onResult: (User?) -> Unit) =
        withContext(Dispatchers.IO){
            val user = userDAO.getUserById(id)
            onResult(user)
        }
}