package com.rodrigoloq.moviepedia.feature.reviews.model

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
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val reviewDAO: ReviewDAO,
    private val userDAO: UserDAO,
    private val movieService: MovieService
) {
    suspend fun addReview(rating: Int,
                          userId: Long,
                          movieId: Int,
                          review: String,
                          onResult: (Boolean) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val result = reviewDAO.addReview(Reviews(rating = rating,
                    userId = userId,
                    movieId = movieId,
                    review = review))
                onResult(result > 0)
            } catch (e: Exception) {
                onResult(false)
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
                onResult(null)
                Log.e("RLTAG", "getMovieDetails: ${e.message}")
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
                onResult(false)
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
                onResult(false)
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