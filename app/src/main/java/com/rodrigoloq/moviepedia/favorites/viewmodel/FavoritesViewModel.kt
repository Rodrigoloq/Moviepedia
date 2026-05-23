package com.rodrigoloq.moviepedia.favorites.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.favorites.model.FavoritesRepository
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.retrofit.entities.Result
import com.rodrigoloq.moviepedia.room.entities.Favorites
import com.rodrigoloq.moviepedia.room.entities.Reviews
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel(){
    private val repository = FavoritesRepository()

    var isLoading by mutableStateOf(false)

    var favoritesData by mutableStateOf<List<MovieDetailResponse>>(emptyList())
        private set

    var isMovieReviewed by mutableStateOf(false)

    fun getUserFavoritesMovies(userId: Long){
        isLoading = true
        viewModelScope.launch {
            repository.getUserFavoritesMovies(userId) { response ->
                if(response != null){
                    favoritesData = response
                }
                isLoading = false
            }
        }
    }

    fun removeFavorite(userId: Long, movieId: Int, onResult:(Boolean) -> Unit){
        var success = true
        viewModelScope.launch {
            repository.removeFavorite(userId, movieId){ result ->
                if (result){
                    success = true
                    getUserFavoritesMovies(userId)
                } else {
                    success = false
                }
            }
            onResult(success)
        }
    }

    fun isMovieReviewed(userId: Long, movieId: Int){
        viewModelScope.launch {
            repository.isMovieReviewed(userId, movieId){
                isMovieReviewed = it
            }
        }
    }
}