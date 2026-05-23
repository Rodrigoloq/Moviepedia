package com.rodrigoloq.moviepedia.moviedetail.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.moviedetail.model.MovieDetailRepository
import com.rodrigoloq.moviepedia.retrofit.entities.AlternativeTitles
import com.rodrigoloq.moviepedia.retrofit.entities.CreditsResponse
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.retrofit.entities.MovieReleasesResponse
import com.rodrigoloq.moviepedia.room.entities.Favorites
import kotlinx.coroutines.launch

class MovieDetailViewModel : ViewModel() {
    private val repository = MovieDetailRepository()
    var isLoading by mutableStateOf(false)
    var movieData by mutableStateOf<MovieDetailResponse?>(null)
        private set
    var altTitlesData by mutableStateOf<List<AlternativeTitles>>(emptyList())
        private set
    var creditsData by mutableStateOf<CreditsResponse?>(null)
        private set
    var releasesData by mutableStateOf<MovieReleasesResponse?>(null)
        private set
    var isFavorite by mutableStateOf(false)
        private set
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

    fun getMovieAlternativeTitle(movieId: Int){
        isLoading = true
        viewModelScope.launch {
            repository.getMovieAlternativeTitles(movieId){ response ->
                if(response != null){
                    altTitlesData = response.titles
                }
            }
        }
    }

    fun getMovieCredits(movieId: Int){
        isLoading = true
        viewModelScope.launch {
            repository.getMovieCredits(movieId){ response ->
                if(response != null){
                    creditsData = response
                }
                isLoading = false
            }
        }
    }

    fun getMovieReleases(movieId: Int){
        isLoading = true
        viewModelScope.launch {
            repository.getMovieReleases(movieId){ response ->
                if(response != null){
                    releasesData = response
                    Log.i("RLTAG", "getMovieReleases: $releasesData")
                }
                isLoading = false
            }
        }
    }

    fun isMovieFavorite(userId: Long, movieId: Int){
        viewModelScope.launch {
            repository.isMovieFavorite(userId, movieId){ isMovieFavorite ->
                isFavorite = isMovieFavorite
            }
        }
    }

    fun addFavorite(favorites: Favorites){
        viewModelScope.launch {
            repository.addFavorite(favorites){ result ->
                if (result != null){
                    isFavorite = result
                }
            }
        }
    }

    fun removeFavorite(userId: Long, movieId: Int){
        viewModelScope.launch {
            repository.removeFavorite(userId, movieId){ result ->
                isFavorite = !result
            }
        }
    }
}