package com.rodrigoloq.moviepedia.reviews.reviews

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.rodrigoloq.moviepedia.R
import com.rodrigoloq.moviepedia.reviews.reviewform.ReviewView
import com.rodrigoloq.moviepedia.reviews.reviews.viewmodel.ReviewsUIState
import com.rodrigoloq.moviepedia.reviews.reviews.viewmodel.ReviewsViewModel
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.ui.theme.MoviepediaTheme

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReviewsViewPreview() {
    MoviepediaTheme() {
        ReviewsViewContent(
            state = ReviewsUIState(),
            modifier = Modifier,
            navController = rememberNavController(),
            movieId = 1,
            userId = 1,
            getAllMovieDetails = {  }
        ) { }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsViewContent(
    state: ReviewsUIState,
    modifier: Modifier = Modifier,
    navController: NavController,
    movieId: Int?,
    userId: Long,
    getAllMovieDetails: (Int) -> Unit,
    deleteReview: (Int) -> Unit
) {
    val context = LocalContext.current
//    val reviewsWithUserOrdered =
//        state.reviewsWithUsers.sortedBy { it.review.userId != MoviepediaApp.auth.id }
    var reviewText by remember { mutableStateOf("") }
    var reviewRating by remember { mutableStateOf(1) }

    var showBottomSheet by remember { mutableStateOf(false) }
    var showReviewForm by remember { mutableStateOf(false) }

    LaunchedEffect(state.successDeletingReview) {
        when (state.successDeletingReview) {
            true -> {
                Toast.makeText(
                    context,
                    "Se ha eliminado la reseña",
                    Toast.LENGTH_SHORT
                ).show()
                getAllMovieDetails(movieId!!)
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

    LaunchedEffect(Unit) {
        getAllMovieDetails(movieId!!)
    }

    val deleteReview = {
        deleteReview(movieId!!)
    }

    Scaffold() {
        Column(
            modifier
                .padding(it)
                .background(Color.Transparent)
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
                    "Reseñas",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            }

            HorizontalDivider()
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/original" + state.movieData.poster_path)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.poster_loading),
                    error = painterResource(R.drawable.error_loading),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .width(100.dp)
                        .height(150.dp)
                )
                if (state.inProgress) {
                    Column(
                        modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(top = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Text(text = "Cargando pelicula...")
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .height(150.dp)
                    ) {
                        Text(
                            text = state.movieData.title,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                        Text(
                            "FECHA DE ESTRENO: ${state.movieData.release_date}",
                            fontSize = 10.sp
                        )
                        Text(
                            state.movieData.overview,
                            fontSize = 10.sp,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            HorizontalDivider()
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
                        "Esta pelicula no tiene ninguna reseña publicada",
                        textAlign = TextAlign.Center
                    )
                }
            } else {
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
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items(state.reviewsWithUsers.size) { i ->
                            val reviewWithUser = state.reviewsWithUsers[i]
                            ReviewItemView(
                                review = reviewWithUser.review,
                                user = reviewWithUser.user,
                                userId = userId
                            ) {
                                showBottomSheet = true
                                reviewText = reviewWithUser.review.review
                                reviewRating = reviewWithUser.review.rating
                            }
                        }
                    }
                }
            }
        }
        if (showReviewForm) {
            ReviewView(
                movie = state.movieData,
                formTitle = "Editar reseña",
                reviewText = reviewText,
                reviewRating = "⭐".repeat(reviewRating),
                newReview = false
            ) { show ->
                if (show == null){
                    showReviewForm = false
                } else {
                    showReviewForm = false
                    getAllMovieDetails(movieId!!)
                }
            }
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(onDismissRequest = {
            showBottomSheet = false
        }) {
            ReviewOptionsView(
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
fun ReviewsView(
    modifier: Modifier = Modifier,
    navController: NavController,
    movieId: Int?,
    viewModel: ReviewsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val userId = viewModel.userId
    ReviewsViewContent(
        state = uiState,
        modifier = modifier,
        navController = navController,
        movieId = movieId,
        userId = userId,
        getAllMovieDetails = { movieId ->
            viewModel.getAllMovieDetails(movieId)
        },
        deleteReview = { movieId ->
            viewModel.deleteReview(movieId =  movieId)
        }
    )
}