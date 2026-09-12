package com.rodrigoloq.moviepedia.moviedetail

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
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
import com.rodrigoloq.moviepedia.moviedetail.viewmodel.MovieDetailUIState
import com.rodrigoloq.moviepedia.moviedetail.viewmodel.MovieDetailViewModel
import com.rodrigoloq.moviepedia.retrofit.entities.GenreMapper
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.entities.Favorites
import com.rodrigoloq.moviepedia.ui.theme.MoviepediaTheme

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieDetailViewPreview() {
    MoviepediaTheme() {
        MovieDetailViewContent(
            state = MovieDetailUIState(),
            navController = rememberNavController(),
            modifier = Modifier,
            movieId = 3,
            getAllMovieInformation = { movieId ->

            },
            addFavorite = { favorite ->

            }
        ) { movieId ->

        }
    }
}

@Composable
fun MovieDetailViewContent(
    state: MovieDetailUIState,
    navController: NavController,
    modifier: Modifier = Modifier,
    movieId: Int?,
    getAllMovieInformation: (Int) -> Unit,
    addFavorite: (Int) -> Unit,
    removeFavorite: (Int) -> Unit
) {

    LaunchedEffect(Unit) {
        getAllMovieInformation(movieId!!)
    }

    val fullSizeDp = LocalConfiguration.current.screenHeightDp.dp

    val genresList = state.movieData.genres
    val genresIdList = genresList.map { it.id }
    val genres = GenreMapper.getGenreNames(genresIdList)

    val colors = listOf(Color.Transparent, Color.Black)
    val tileSize = with(LocalDensity.current) {
        (fullSizeDp / 2).toPx()
    }

    val options = listOf("Detalles", "Elenco", "Equipo", "Estreno")
    var selectedIndex by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()

    var maxLines by remember { mutableIntStateOf(3) }

    Scaffold() {
        Box(
            modifier = modifier
                .padding(bottom = it.calculateBottomPadding())
                .fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/original" + state.movieData.poster_path)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                placeholder = painterResource(R.drawable.poster_loading),
                error = painterResource(R.drawable.no_poster_detail),
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,
                modifier = Modifier
            )
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = colors,
                            endY = tileSize, tileMode = TileMode.Clamp
                        )
                    )
            ) { }
            if (state.inProgress) {
                Column(
                    modifier
                        .fillMaxSize()
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
                        .fillMaxSize()
                        .padding(end = 8.dp, start = 8.dp, top = fullSizeDp / 3),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.movieData.title,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    Text(
                        text = state.movieData.release_date.take(4),
                        color = Color.White
                    )
                    Row(modifier = Modifier.padding(vertical = 8.dp)) {
                        genres.take(3).forEach { genre ->
                            Button(
                                onClick = {}, enabled = false,
                                colors = ButtonDefaults.buttonColors(
                                    disabledContentColor = Color.LightGray
                                ),
                                modifier = Modifier.padding(end = 4.dp),
                                contentPadding = PaddingValues(8.dp),
                            ) {
                                Text(text = genre, fontSize = 15.sp)
                            }
                        }
                        Button(
                            onClick = {}, enabled = false, contentPadding = PaddingValues(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                disabledContentColor = Color.LightGray
                            )
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, "")
                                Text(
                                    text = "%.1f".format(state.movieData.vote_average),
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                    Column(Modifier.verticalScroll(scrollState)) {
                        Text(
                            state.movieData.overview,
                            modifier = Modifier.clickable {
                                if (maxLines == Int.MAX_VALUE) maxLines = 3 else maxLines =
                                    Int.MAX_VALUE
                            },
                            maxLines = maxLines,
                            overflow = TextOverflow.Ellipsis
                        )

                        SingleChoiceSegmentedButtonRow(Modifier.padding(vertical = 8.dp)) {
                            options.forEachIndexed { index, label ->
                                SegmentedButton(
                                    icon = {},
                                    colors = SegmentedButtonDefaults.colors(
                                        activeContainerColor = Color.DarkGray
                                    ),
                                    selected = selectedIndex == index,
                                    onClick = { selectedIndex = index },
                                    shape = RectangleShape
                                ) {
                                    Text(label)
                                }
                            }
                        }
                        when (selectedIndex) {
                            0 -> {
                                DetailView(
                                    Modifier.fillMaxHeight(),
                                    movie = state.movieData,
                                    altTitles = state.alternativeTitles
                                )
                            }

                            1 -> {
                                LazyRow() {
                                    items(state.credits.cast.size) { i ->
                                        val cast = state.credits.cast[i]
                                        CastView(cast = cast)
                                    }
                                }
                            }

                            2 -> {
                                CrewView(crew = state.credits.crew)
                            }

                            3 -> {
                                ReleasesView(releases = state.releases.results)
                            }
                        }
                    }
                }
            }
            Box(
                Modifier
                    .padding(top = it.calculateTopPadding(), start = 10.dp)
                    .background(Color.LightGray.copy(alpha = 0.3f), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    painter = painterResource(R.drawable.ic_back_arrow),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .clickable {
                            navController.popBackStack()
                        })
            }
            Box(
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = it.calculateTopPadding(), end = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Column() {
                    Box(
                        Modifier.background(
                            Color.LightGray.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                    ) {
                        Icon(
                            painter = painterResource(if (!state.isFavorite) R.drawable.ic_favorite_off else R.drawable.ic_favorite_on),
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(4.dp)
                                .clickable {
                                    if (state.isFavorite) {
                                        removeFavorite(movieId!!)
                                    } else {
                                        addFavorite(movieId!!)
                                    }
                                })
                    }

                    Box(
                        Modifier
                            .padding(top = 8.dp)
                            .background(Color.LightGray.copy(alpha = 0.3f), shape = CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_review),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(4.dp)
                                .clickable {
                                    navController.navigate("review" + "/${state.movieData.id}")
                                })
                    }
                }
            }
        }
    }
}

@Composable
fun MovieDetailView(
    navController: NavController,
    modifier: Modifier = Modifier,
    movieId: Int?,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    MovieDetailViewContent(
        state = uiState,
        navController = navController,
        modifier = modifier,
        movieId = movieId,
        getAllMovieInformation = { movieId ->
            viewModel.getAllMovieInformation(movieId = movieId)
        },
        addFavorite = { movieId ->
            viewModel.addFavorite(movieId = movieId)
        },
        removeFavorite = { movieId ->
            viewModel.removeFavorite(movieId = movieId)
        }
    )

}