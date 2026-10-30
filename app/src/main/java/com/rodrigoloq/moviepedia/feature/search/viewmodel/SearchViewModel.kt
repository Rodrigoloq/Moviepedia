package com.rodrigoloq.moviepedia.feature.search.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoloq.moviepedia.retrofit.entities.Result
import com.rodrigoloq.moviepedia.feature.search.model.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(SearchUIState())
    val uiState: StateFlow<SearchUIState> = _uiState.asStateFlow()

    init {
        getListTopRatedMovies()
    }

    fun getListSearchResult(movie: String){
        _uiState.update { it.copy(inProgress = true) }
        viewModelScope.launch {
            repository.getListSearchResult(movie) { response ->
                if(response != null){
                    _uiState.update { it.copy(searchResults = response.results) }
                }
            }
            _uiState.update { it.copy(inProgress = false) }
        }
    }

    fun getListTopRatedMovies(){
        _uiState.update { it.copy(inProgress = true) }
        viewModelScope.launch {
            repository.getListTopRatedMovies { result ->
                if(result != null){
                    _uiState.update { it.copy(topRatedMovies = result) }
                }
            }
            _uiState.update { it.copy(inProgress = false) }
        }
    }
}