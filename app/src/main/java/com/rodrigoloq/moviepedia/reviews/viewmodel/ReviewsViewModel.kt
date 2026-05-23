package com.rodrigoloq.moviepedia.reviews.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.reviews.model.ReviewRepository
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.entities.Reviews
import com.rodrigoloq.moviepedia.room.entities.User
import kotlinx.coroutines.launch

class ReviewsViewModel : ViewModel() {
    private val repository = ReviewRepository()
    var isLoading by mutableStateOf(false)
    var reviewsData by mutableStateOf<List<Reviews>?>(null)
        private set
    var movieData by mutableStateOf<MovieDetailResponse?>(null)
        private set

    var userReviewMap by mutableStateOf<Map<Reviews, User>>(emptyMap())
        private set
    fun getReviewsByMovie(movieId: Int){
        isLoading = true
        viewModelScope.launch {
            repository.getReviewsByMovie(movieId){reviews ->
                reviewsData = reviews
                reviews?.forEach { review ->
                    getUserById(review.userId){user ->
                        userReviewMap = userReviewMap + (review to user)
                    }
                }
                isLoading = false
            }
        }
    }

    fun getUserById(id: Long, onResult:(User) -> Unit){
        viewModelScope.launch {
            repository.getUserById(id){ user ->
                onResult(user ?: User(username = "", email = "", password = ""))
            }
        }
    }

    fun getMovieDetail(movieId: Int){
        isLoading = true
        viewModelScope.launch {
            repository.getMovieDetail(movieId){ response ->
                if (response != null){
                    movieData = response
                }
                isLoading = false
            }
        }
    }

    fun deleteReview(userId: Long,
                     movieId: Int,
                     onResult: (Boolean) -> Unit){
        isLoading = true
        var success = false
        viewModelScope.launch {
            repository.deleteReview(userId, movieId){
                success = it
            }
            onResult(success)
            isLoading = false
        }
    }
}