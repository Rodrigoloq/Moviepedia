package com.rodrigoloq.moviepedia.feature.reviews.reviewform

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.newStringBuilder
import com.rodrigoloq.moviepedia.R
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.feature.reviews.reviewform.viewmodel.ReviewFormUIState
import com.rodrigoloq.moviepedia.feature.reviews.reviewform.viewmodel.ReviewFormViewModel
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.entities.Reviews
import com.rodrigoloq.moviepedia.ui.theme.MoviepediaTheme

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReviewViewPreview() {
    MoviepediaTheme() {
        Surface() {
            ReviewViewContent(
                state = ReviewFormUIState(),
                modifier = Modifier,
                movie = MovieDetailResponse(),
                reviewText = "",
                reviewRating = "Calificacion",
                newReview = true,
                formTitle = "",
                editReview = {movieId, newReview, newRating ->
                },
                addReview = { rating, movieId, review ->
                }
            ) { }
        }
    }
}

@Composable
fun ReviewViewContent(state: ReviewFormUIState,
                      modifier: Modifier,
                      movie: MovieDetailResponse,
                      reviewText: String = "",
                      reviewRating: String = "Calificacion",
                      newReview: Boolean = true,
                      formTitle: String,
                      editReview: (Int, String, Int) -> Unit,
                      addReview: (Int, Int, String) -> Unit,
                      onShowReview: (Boolean?) -> Unit){

    val context = LocalContext.current

    LaunchedEffect(state.successEditingReview) {
        when(state.successEditingReview){
            true -> {
                Toast.makeText(
                    context, "La reseña se ha editado correctamente",
                    Toast.LENGTH_SHORT
                ).show()
                onShowReview(false)
            }
            false -> {
                Toast.makeText(
                    context, "Error al editar la reseña",
                    Toast.LENGTH_SHORT
                ).show()
            }
            null -> Unit
        }
    }

    LaunchedEffect(state.successAddingReview) {
        when(state.successAddingReview){
            true -> {
                Toast.makeText(
                    context, "La reseña se ha publicado correctamente",
                    Toast.LENGTH_SHORT
                ).show()
                onShowReview(false)
            }
            false -> {
                Toast.makeText(
                    context, "Error al publicar la reseña",
                    Toast.LENGTH_SHORT
                ).show()
            }
            null -> Unit
        }
    }


    var reviewText by remember { mutableStateOf(reviewText) }
    var expanded by remember { mutableStateOf(false) }
    var rating by remember { mutableStateOf(reviewRating) }

    val editReview = {
        if (!reviewText.trim().isEmpty()) {
            if (!rating.equals("Calificacion")) {
                editReview(movie.id,
                    reviewText,
                    rating.length)
            } else {
                Toast.makeText(
                    context, "Selecciona una calificacion",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                context, "Escriba una reseña",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    val postReview = {
        if (!reviewText.trim().isEmpty()) {
            if (!rating.equals("Calificacion")) {
                addReview(
                    rating.length,
                    movie.id,
                    reviewText,
                )
            } else {
                Toast.makeText(
                    context, "Selecciona una calificacion",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                context, "Escriba una reseña",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    MoviepediaTheme() {
        Box(
            modifier
                .fillMaxSize()
                .background(Color(0x80808080))
                .clickable(interactionSource = null, indication = null) {},
            contentAlignment = Alignment.Center
        ) {
            if (!state.inProgress) {
                Column(
                    Modifier
                        .padding(horizontal = 8.dp)
                        .background(
                            color = Color(0xFF121212),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Text(
                        formTitle,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                    Text(
                        "${movie.title} • ${movie.release_date.take(4)}",
                        Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Box(
                        Modifier
                            .padding(start = 8.dp, bottom = 8.dp)
                            .border((1).dp, Color.DarkGray)
                            .clickable {
                                expanded = true
                            }) {
                        Row(Modifier.padding(4.dp)) {
                            Text(rating)
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_rating),
                                ""
                            )
                        }
                        DropdownMenu(
                            modifier = Modifier.padding(4.dp),
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("⭐") },
                                onClick = {
                                    rating = "⭐"
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("⭐⭐") },
                                onClick = {
                                    rating = "⭐⭐"
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("⭐⭐⭐") },
                                onClick = {
                                    rating = "⭐⭐⭐"
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("⭐⭐⭐⭐") },
                                onClick = {
                                    rating = "⭐⭐⭐⭐"
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("⭐⭐⭐⭐⭐") },
                                onClick = {
                                    rating = "⭐⭐⭐⭐⭐"
                                    expanded = false
                                }
                            )
                        }
                    }
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .height(168.dp),
                        value = reviewText,
                        onValueChange = {
                            reviewText = it
                        },
                        shape = RoundedCornerShape(8.dp)

                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = { onShowReview(null) }) {
                            Text("Cancelar")
                        }
                        FilledTonalButton(
                            modifier = Modifier.padding(start = 8.dp),
                            onClick = {
                                if (newReview){
                                    postReview()
                                } else {
                                    editReview()
                                }
                            }) {
                            Text("Publicar")
                        }
                    }
                }
            } else {
                Column(
                    Modifier
                        .background(Color.Transparent)
                        .fillMaxSize()
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun ReviewView(
    modifier: Modifier = Modifier,
    movie: MovieDetailResponse,
    reviewText: String = "",
    reviewRating: String = "Calificacion",
    newReview: Boolean = true,
    formTitle: String,
    viewModel: ReviewFormViewModel = hiltViewModel(),
    onShowReview: (Boolean?) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    ReviewViewContent(
        state = uiState,
        modifier = modifier,
        movie = movie,
        reviewText = reviewText,
        reviewRating = reviewRating,
        newReview = newReview,
        formTitle = formTitle,
        editReview = { movieId, newReview, newRating ->
            viewModel.editReview(movieId = movieId,
                newReview = newReview,
                newRating = newRating)
        },
        addReview = {rating, movieId, review ->
            viewModel.addReview(rating = rating,
                movieId = movieId,
                review = review)
        },
        onShowReview = {show ->
            onShowReview(show)
        }
    )

}