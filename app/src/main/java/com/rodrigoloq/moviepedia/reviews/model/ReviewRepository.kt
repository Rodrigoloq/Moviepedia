package com.rodrigoloq.moviepedia.reviews.model

import android.util.Log
import com.rodrigoloq.moviepedia.retrofit.MovieService
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.room.FavoritesDAO
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.ReviewDAO
import com.rodrigoloq.moviepedia.room.UserDAO
import com.rodrigoloq.moviepedia.room.entities.Favorites
import com.rodrigoloq.moviepedia.room.entities.Reviews
import com.rodrigoloq.moviepedia.room.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewRepository {
    private val reviewDAO: ReviewDAO by lazy { MoviepediaApp.ldb.ReviewDAO() }
    private val userDAO: UserDAO by lazy { MoviepediaApp.ldb.userDAO() }
    private val movieService: MovieService by lazy { MoviepediaApp.rdb.create(MovieService::class.java)}

    suspend fun addReview(reviews: Reviews, onResult: (Boolean) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val result = reviewDAO.addReview(reviews)
                onResult(result > 0)
            } catch (e: Exception) {
                Log.e("RLTAG", "addReview: ${e.message}")
            }
        }

    suspend fun getReviewsByMovie(movieId: Int, onResult: (List<Reviews>?) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val reviews = reviewDAO.getReviewsByMovie(movieId)
                onResult(reviews)
            } catch (e: Exception) {
                onResult(null)
                Log.e("RLTAG", "getReviewsByMovie: ${e.message}")
            }
        }

    suspend fun getUserById(id: Long, onResult: (User?) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val user = userDAO.getUserById(id)
                onResult(user)
            } catch (e: Exception) {
                onResult(null)
                Log.e("RLTAG", "getUserInfo: ${e.message}")
            }
        }

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

    suspend fun editReview(userId: Long,
                           movieId: Int,
                           newReview: String, newRating: Int, onResult: (Boolean) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val result = reviewDAO.updateReview(userId, movieId, newReview, newRating)
                onResult(result > 0)
            } catch (e: Exception) {
                Log.e("RLTAG", "addReview: ${e.message}")
            }
        }

    suspend fun deleteReview(userId: Long,
                           movieId: Int,
                           onResult: (Boolean) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val result = reviewDAO.deleteReview(userId, movieId)
                onResult(result > 0)
            } catch (e: Exception) {
                Log.e("RLTAG", "addReview: ${e.message}")
            }
        }

    suspend fun getReviewsByUser(userId: Long, onResult: (List<Reviews>?) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val reviews = reviewDAO.getReviewsByUser(userId)
                onResult(reviews)
            } catch (e: Exception) {
                onResult(null)
                Log.e("RLTAG", "getReviewsByMovie: ${e.message}")
            }
        }

}