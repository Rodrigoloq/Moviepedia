package com.rodrigoloq.moviepedia.feature.favorites

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rodrigoloq.moviepedia.R
import com.rodrigoloq.moviepedia.feature.favorites.viewmodel.FavoritesUIState
import com.rodrigoloq.moviepedia.feature.favorites.viewmodel.FavoritesViewModel
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.feature.reviews.reviewform.ReviewView
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.ui.theme.MoviepediaTheme

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun FavoritesViewPreview() {
    MoviepediaTheme() {
        Scaffold() {
            FavoritesViewContent(
                state = FavoritesUIState(),
                navController = rememberNavController(),
                modifier = Modifier.padding(it),
                removeFavorite = { movieId -> },
                getUserFavoritesMovies = {}
            ) { movieId -> }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesViewContent(
    state: FavoritesUIState,
    navController: NavController,
    modifier: Modifier,
    removeFavorite: (Int) -> Unit,
    getUserFavoritesMovies: () -> Unit,
    isMovieReviewed: (Int) -> Unit
) {
    val context = LocalContext.current
    var showBottomSheet by remember { mutableStateOf(false) }
    var showReviewForm by remember { mutableStateOf(false) }
    var movieSelected by remember { mutableStateOf(MovieDetailResponse()) }

    LaunchedEffect(Unit) {
        getUserFavoritesMovies()
    }

    LaunchedEffect(movieSelected) {
        isMovieReviewed(movieSelected.id)
    }

    LaunchedEffect(state.successRemovingFavorite) {
        when (state.successRemovingFavorite) {
            true -> {
                Toast.makeText(
                    context,
                    "Se elimino la pelicula de su lista de favoritos",
                    Toast.LENGTH_SHORT
                ).show()
                showBottomSheet = false
            }

            false -> {
                Toast.makeText(
                    context,
                    "Error al eliminar la pelicula de su lista de favoritos",
                    Toast.LENGTH_SHORT
                ).show()
                showBottomSheet = false
            }

            null -> Unit
        }
    }

    Box {
        if (state.inProgress) {
            Column(
                modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text(text = "Cargando peliculas...")
            }
        } else {
            if (state.favorites.isEmpty()) {
                Column(
                    modifier
                        .fillMaxSize()
                        .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.favorite_not_found),
                        null,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        "Aun no tienes peliculas en tu lista de favoritos",
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Column(
                    modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text("Peliculas favoritas", modifier = Modifier.padding(bottom = 8.dp))
                    HorizontalDivider()
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items(state.favorites.size) { i ->
                            val movie = state.favorites[i]
                            FavoriteMoviesItemView(movie = movie) { show, movie ->
                                showBottomSheet = show
                                movieSelected = movie
                            }
                        }
                    }
                }
            }
        }
        if (showReviewForm) {
            ReviewView(
                movie = movieSelected,
                formTitle = "Escriba una reseña"
            ) { show ->
                if (show == null) {
                    showReviewForm = false
                } else {
                    showReviewForm = show
                    isMovieReviewed(movieSelected.id)
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(onDismissRequest = {
            showBottomSheet = false
        }) {
            FavoritesOptionsView(
                movie = movieSelected,
                navController = navController,
                isMovieReviewed = state.isMovieReviewed,
                removeFavorite = {
                    removeFavorite(
                        movieSelected.id
                    )
                },
                onDismiss = {
                    showBottomSheet = false
                },
                onShowReview = {
                    showReviewForm = true
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesView(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    FavoritesViewContent(
        state = uiState,
        navController = navController,
        modifier = modifier,
        removeFavorite = { movieId ->
            viewModel.removeFavorite(movieId = movieId)
        },
        getUserFavoritesMovies = {
            viewModel.getUserFavoritesMovies()
        },
        isMovieReviewed = { movieId ->
            viewModel.isMovieReviewed(movieId = movieId)
        }
    )
}

