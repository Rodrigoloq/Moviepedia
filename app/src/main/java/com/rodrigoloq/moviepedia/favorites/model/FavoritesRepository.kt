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
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val favoritesDAO: FavoritesDAO,
    private val reviewDAO: ReviewDAO,
    private val movieService: MovieService
) {
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
                onResult(null)
                Log.e("RLTAG", "getUserFavoritesMovies: ${e.message}")
            }
    }

    suspend fun removeFavorite(userId: Long, movieId: Int, onResult: (Boolean) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val deleteRows = favoritesDAO.removeFavorite(userId, movieId)
                onResult(deleteRows > 0)
            } catch (e: Exception) {
                onResult(false)
                Log.e("RLTAG", "removeFavorite: ${e.message}")
            }
        }

    suspend fun isMovieReviewed(userId: Long, movieId: Int, onResult: (Boolean) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val result = reviewDAO.isMovieReviewed(userId, movieId)
                onResult(result)
            } catch (e: Exception){
                onResult(false)
                Log.e("RLTAG", "isMovieReviewed: ${e.message}")
            }
        }

}