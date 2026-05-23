package com.rodrigoloq.moviepedia.moviedetail.model

import android.graphics.Movie
import android.util.Log
import com.rodrigoloq.moviepedia.retrofit.MovieService
import com.rodrigoloq.moviepedia.retrofit.entities.AlternativeTitlesResponse
import com.rodrigoloq.moviepedia.retrofit.entities.CreditsResponse
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.retrofit.entities.MovieReleasesResponse
import com.rodrigoloq.moviepedia.room.FavoritesDAO
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.UserDAO
import com.rodrigoloq.moviepedia.room.entities.Favorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDetailRepository {
    private val movieService: MovieService by lazy { MoviepediaApp.rdb.create(MovieService::class.java)}
    private val favoritesDAO: FavoritesDAO by lazy { MoviepediaApp.ldb.FavoritesDAO() }

    suspend fun getMovieDetail(movieId: Int, onResult:(MovieDetailResponse?) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val response = movieService.getMovieDetail(movieId)
                onResult(response)
            } catch (e: Exception) {
                Log.e("RLTAG", "getMovieDetails: ${e.message}")
                onResult(null)
            }
    }

    suspend fun getMovieAlternativeTitles(movieId: Int, onResult: (AlternativeTitlesResponse?) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val response = movieService.getMovieAlternativeTitles(movieId)
                onResult(response)
            } catch (e: Exception){
                Log.e("RLTAG", "getMovieAlternativeTitles: ${e.message}")
                onResult(null)
            }
        }

    suspend fun getMovieCredits(movieId: Int, onResult: (CreditsResponse?) -> Unit) =
        withContext(Dispatchers.IO){
        try {
            val response = movieService.getMovieCredits(movieId)
            onResult(response)
        }catch (e: Exception){
            Log.e("RLTAG", "getMovieCredits: ${e.message}")
            onResult(null)
        }
    }

    suspend fun getMovieReleases(movieId: Int, onResult: (MovieReleasesResponse?) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val response = movieService.getMovieReleases(movieId)
                onResult(response)
            }catch (e: Exception){
                Log.e("RLTAG", "getMovieReleases: ${e.message}")
                onResult(null)
            }
        }

    suspend fun isMovieFavorite(userId: Long, movieId: Int, onResult: (Boolean) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val isFavorite = favoritesDAO.isMovieFavorite(userId,movieId)
                onResult(isFavorite)
            } catch (e: Exception) {
                Log.e("RLTAG", "isFavorite: ${e.message}")
            }
        }

    suspend fun addFavorite(favorites: Favorites, onResult: (Boolean?) -> Unit) =
        withContext(Dispatchers.IO){
            if(favoritesDAO.isMovieFavorite(favorites.userId,favorites.movieId)){
                onResult(null)
            } else {
                val newId = favoritesDAO.addFavorite(favorites)
                onResult(newId > 0)
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
}