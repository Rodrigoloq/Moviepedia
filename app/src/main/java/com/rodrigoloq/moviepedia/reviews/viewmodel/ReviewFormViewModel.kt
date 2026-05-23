package com.rodrigoloq.moviepedia.reviews.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.reviews.model.ReviewRepository
import com.rodrigoloq.moviepedia.room.entities.Reviews
import kotlinx.coroutines.launch

class ReviewFormViewModel : ViewModel() {
    private val repository = ReviewRepository()
    var isLoading by mutableStateOf(false)

    fun addReview(reviews: Reviews, onResult: (Boolean) -> Unit){
        isLoading = true
        var success = false
        viewModelScope.launch {
            repository.addReview(reviews){
                success = it
            }
            onResult(success)
            isLoading = false
        }
    }

    fun editReview(userId: Long,
                   movieId: Int,
                   newReview: String, newRating: Int, onResult: (Boolean) -> Unit){
        isLoading = true
        var success = false
        viewModelScope.launch {
            repository.editReview(userId, movieId, newReview, newRating){
                success = it
            }
            onResult(success)
            isLoading = false
        }
    }


}