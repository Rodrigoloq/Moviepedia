package com.rodrigoloq.moviepedia.favorites.model

import android.util.Log
import com.rodrigoloq.moviepedia.retrofit.MovieService
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.room.FavoritesDAO
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.ReviewDAO
import com.rodrigoloq.moviepedia.room.UserDAO
import com.rodrigoloq.moviepedia.room.entities.Favorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritesRepository {
    private val favoritesDAO: FavoritesDAO by lazy { MoviepediaApp.ldb.FavoritesDAO() }
    private val reviewDAO: ReviewDAO by lazy { MoviepediaApp.ldb.ReviewDAO() }
    private val movieService: MovieService by lazy { MoviepediaApp.rdb.create(MovieService::class.java)}

    suspend fun getUserFavoritesMovies(userId: Long, onResult:(List<MovieDetailResponse>?) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val response = favoritesDAO.getFavoritesByUser(userId)
                var favoriteMovies: List<MovieDetailResponse> = listOf()
                response.forEach {movieId ->
                    favoriteMovies = favoriteMovies + movieService.getMovieDetail(movieId)
                }
                onResult(favoriteMovies)
            }catch (e: Exception){
                Log.e("RLTAG", "getUserFavoritesMovies: ${e.message}")
                onResult(null)
            }
    }

    suspend fun removeFavorite(userId: Long, movieId: Int, onResult: (Boolean) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val deleteRows = favoritesDAO.removeFavorite(userId, movieId)
                onResult(deleteRows > 0)
            } catch (e: Exception) {
                Log.e("RLTAG", "removeFavorite: ${e.message}")
            }
        }

    suspend fun isMovieReviewed(userId: Long, movieId: Int, onResult: (Boolean) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val result = reviewDAO.isMovieReviewed(userId, movieId)
                onResult(result)
            } catch (e: Exception){
                Log.e("RLTAG", "isMovieReviewed: ${e.message}")
            }
        }

}