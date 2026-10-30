package com.rodrigoloq.moviepedia.feature.reviews.userreviews

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rodrigoloq.moviepedia.R
import com.rodrigoloq.moviepedia.data.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.feature.reviews.reviews.ReviewOptionsView
import com.rodrigoloq.moviepedia.feature.reviews.reviewform.ReviewView
import com.rodrigoloq.moviepedia.feature.reviews.userreviews.viewmodel.UserReviewsUIState
import com.rodrigoloq.moviepedia.feature.reviews.userreviews.viewmodel.UserReviewsViewModel
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.ui.theme.MoviepediaTheme

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun UserReviewsViewPreview() {
    MoviepediaTheme() {
        UserReviewsViewContent(
            state = UserReviewsUIState(),
            navController = rememberNavController(),
            modifier = Modifier,
            getReviewsByUser = {  },
            deleteReview = {  }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserReviewsViewContent(
    state: UserReviewsUIState,
    navController: NavController,
    modifier: Modifier,
    getReviewsByUser: () -> Unit,
    deleteReview: (Int) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(state.successDeletingReview) {
        when (state.successDeletingReview) {
            true -> {
                Toast.makeText(
                    context,
                    "Se ha eliminado la reseña",
                    Toast.LENGTH_SHORT
                ).show()
                getReviewsByUser()
            }

            false -> {
                Toast.makeText(
                    context,
                    "Error al eliminar la reseña",
                    Toast.LENGTH_SHORT
                ).show()
            }

            null -> Unit
        }
    }

    var showBottomSheet by remember { mutableStateOf(false) }
    var showReviewForm by remember { mutableStateOf(false) }

    var movieSelected by remember { mutableStateOf(MovieDetailResponse()) }
    var reviewText by remember { mutableStateOf("") }
    var reviewRating by remember { mutableIntStateOf(1) }

    LaunchedEffect(Unit) {
        getReviewsByUser()
    }

    val deleteReview = {
        deleteReview(movieSelected.id)
    }

    Scaffold() { it ->
        Column(
            modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            Box() {
                Icon(
                    painterResource(R.drawable.ic_back_arrow),
                    "",
                    Modifier
                        .padding(start = 4.dp)
                        .size(25.dp)
                        .clickable {
                            navController.popBackStack()
                        })
                Text(
                    "Tus reseñas",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            }
            HorizontalDivider()
            if (state.inProgress) {
                Column(
                    modifier
                        .fillMaxSize()
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Text(text = "Cargando reseñas...")
                }
            } else {
                if (state.reviews.isEmpty()) {
                    Column(
                        modifier
                            .fillMaxSize()
                            .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Image(
                            painter = painterResource(R.drawable.favorite_not_found),
                            null,
                            modifier = Modifier.size(64.dp)
                        )
                        Text(
                            "No tienes ninguna reseña publicada",
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items(state.reviewsWithMovies.size) { i ->
                            val reviewWithMovie = state.reviewsWithMovies[i]
                            UserReviewItemView(
                                review = reviewWithMovie.review,
                                movie = reviewWithMovie.movie
                            ) { movie, review, rating ->
                                movieSelected = movie
                                reviewText = review
                                reviewRating = rating
                                showBottomSheet = true
                            }
                        }
                    }
                }
            }
        }
        if (showReviewForm) {
            ReviewView(
                movie = movieSelected,
                formTitle = "Editar reseña",
                reviewText = reviewText,
                reviewRating = "⭐".repeat(reviewRating),
                newReview = false
            ) { show ->
                if (show == null) {
                    showReviewForm = false
                } else {
                    showReviewForm = false
                    getReviewsByUser()
                }
            }
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(onDismissRequest = {
            showBottomSheet = false
        }) {
            ReviewOptionsView(
                isUserReviews = true,
                navController = navController,
                movieId = movieSelected.id,
                onDismiss = { showBottomSheet = false },
                onShowReview = { showReviewForm = true },
                onDeleteReview = {
                    deleteReview()
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserReviewsView(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: UserReviewsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    UserReviewsViewContent(
        state = uiState,
        navController = navController,
        modifier = modifier,
        getReviewsByUser = {
            viewModel.getReviewsByUser()
        },
        deleteReview = { movieId ->
            viewModel.deleteReview(movieId = movieId)
        }
    )
}