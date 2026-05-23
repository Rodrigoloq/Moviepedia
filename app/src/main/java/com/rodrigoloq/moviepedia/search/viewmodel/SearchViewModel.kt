package com.rodrigoloq.moviepedia.search.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.retrofit.entities.Result
import com.rodrigoloq.moviepedia.search.model.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = SearchRepository()
    var searchResults by mutableStateOf<List<Result>>(emptyList())
        private set
    var topRatedMovies by mutableStateOf<List<Result>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)

    fun getListSearchResult(movie: String){
        isLoading = true
        viewModelScope.launch {
            repository.getListSearchResult(movie) { response ->
                if(response != null){
                    searchResults = response.results
                }
                isLoading = false
            }
        }
    }

    fun getListTopRatedMovies(){
        isLoading = true
        viewModelScope.launch {
            repository.getListTopRatedMovies { result ->
                if(result != null){
                    topRatedMovies = result
                }
                isLoading = false
            }
        }
    }
}