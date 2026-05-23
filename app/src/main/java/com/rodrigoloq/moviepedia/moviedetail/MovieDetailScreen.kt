package com.rodrigoloq.moviepedia.moviedetail

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.rodrigoloq.moviepedia.R
import com.rodrigoloq.moviepedia.moviedetail.viewmodel.MovieDetailViewModel
import com.rodrigoloq.moviepedia.retrofit.entities.GenreMapper
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.entities.Favorites
import com.rodrigoloq.moviepedia.ui.theme.MoviepediaTheme

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieDetailViewPreview(){
    MoviepediaTheme() {
        MovieDetailView(rememberNavController(),
            Modifier,
            2)
    }
}

@Composable
fun MovieDetailView(navController: NavController,
                    modifier: Modifier = Modifier,
                    movieId: Int?,
                    viewModel: MovieDetailViewModel = viewModel()){

//    val movie = Result(
//        adult = false,
//        backdrop_path = "asd",
//        genre_ids = listOf(10751,35,12,14,12),
//        id = 123,
//        original_language = "en",
//        original_title = "asd",
//        overview = "asd",
//        popularity = 1.2f,
//        poster_path = "asd",
//        release_date = "asd",
//        title = "asdfasd",
//        video = false,
//        vote_average = 5.3f,
//        vote_count = 100
//    )

    val movie = viewModel.movieData
    val altTitles = viewModel.altTitlesData
    val cast = viewModel.creditsData?.cast ?: listOf()
    val crew = viewModel.creditsData?.crew ?: listOf()
    val releases = viewModel.releasesData?.results ?: listOf()
    var isFavorite = viewModel.isFavorite

    Log.i("RLTAG", "MovieDetailView: $isFavorite")

    LaunchedEffect(Unit) {
        viewModel.getMovieDetail(movieId!!)
        viewModel.getMovieAlternativeTitle(movieId)
        viewModel.getMovieCredits(movieId)
        viewModel.getMovieReleases(movieId)
        viewModel.isMovieFavorite(MoviepediaApp.auth.id, movieId)
    }

    val fullSizeDp = LocalConfiguration.current.screenHeightDp.dp

    val genresList = movie?.genres ?: listOf()
    val genresIdList = genresList.map { it.id }
    val genres = GenreMapper.getGenreNames(genresIdList)

    val colors = listOf(Color.Transparent, Color.Black)
    val tileSize = with(LocalDensity.current) {
        (fullSizeDp / 2).toPx()
    }

    val options = listOf("Detalles", "Elenco", "Equipo", "Estreno")
    var selectedIndex by remember { mutableIntStateOf(0)}
    val scrollState = rememberScrollState()

    var maxLines by remember { mutableIntStateOf(3) }

    Scaffold() {
        Box(modifier = modifier
            .padding(bottom = it.calculateBottomPadding())
            .fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/original" + movie?.poster_path)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                placeholder = painterResource(R.drawable.poster_loading),
                error = painterResource(R.drawable.no_poster_detail),
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,
                modifier = Modifier
            )
            Canvas(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = colors,
                        endY = tileSize, tileMode = TileMode.Clamp
                    )
                )) { }
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(end = 8.dp, start = 8.dp, top = fullSizeDp / 3),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = movie?.title ?: "Titulo",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White)
                Text(text = movie?.release_date?.take(4) ?: "2020",
                    color = Color.White)
                Row(modifier = Modifier.padding(vertical = 8.dp)) {
                    genres.take(3).forEach {genre ->
                        Button(onClick = {}, enabled = false,
                            colors = ButtonDefaults.buttonColors(
                                disabledContentColor = Color.LightGray
                            ),
                            modifier = Modifier.padding(end = 4.dp),
                            contentPadding = PaddingValues(8.dp),
                        ) {
                            Text(text = genre, fontSize = 15.sp)
                        }
                    }
                    Button(onClick = {}, enabled = false, contentPadding = PaddingValues(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            disabledContentColor = Color.LightGray
                        )) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, "")
                            Text(text = "%.1f".format(movie?.vote_average ?: 8.203),
                                fontSize = 15.sp)
                        }
                    }
                }
                Column(Modifier.verticalScroll(scrollState)) {
                    Text(movie?.overview ?: "Descripcion",
                        modifier = Modifier.clickable{
                            if (maxLines == Int.MAX_VALUE) maxLines = 3 else maxLines = Int.MAX_VALUE
                        },
                        maxLines = maxLines,
                        overflow = TextOverflow.Ellipsis)

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
                    when(selectedIndex){
                        0 -> {
                            DetailView(
                                Modifier.fillMaxHeight(),
                                movie = movie,
                                altTitles = altTitles
                            )
                        }
                        1 -> {
                            LazyRow() {
                                items(cast.size){i ->
                                    val cast = cast[i]
                                    CastView(cast = cast)
                                }
                            }
                        }
                        2 -> {
                            CrewView(crew = crew)
                        }
                        3 -> {
                            ReleasesView(releases = releases)
                        }
                    }
                }
            }
            Box(Modifier.padding(top = it.calculateTopPadding(), start = 10.dp)
                .background(Color.LightGray.copy(alpha = 0.3f), shape = CircleShape),
                contentAlignment = Alignment.Center
                ){

                Icon(painter = painterResource(R.drawable.ic_back_arrow),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp).padding(4.dp)
                        .clickable {
                            navController.popBackStack()
                        })
            }
            Box(Modifier.align(Alignment.TopEnd).padding(top = it.calculateTopPadding(), end = 10.dp),
                contentAlignment = Alignment.Center
            ){
                Column() {
                    Box(Modifier.background(Color.LightGray.copy(alpha = 0.3f), shape = CircleShape)) {
                        Icon(painter = painterResource(if(!isFavorite) R.drawable.ic_favorite_off else R.drawable.ic_favorite_on),
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.size(40.dp).padding(4.dp)
                                .clickable {
                                    if (isFavorite){
                                        viewModel.removeFavorite(userId = MoviepediaApp.auth.id, movieId = movieId!!)
                                    } else {
                                        viewModel.addFavorite(Favorites(userId = MoviepediaApp.auth.id, movieId = movieId!!))
                                    }
                                })
                    }

                    Box(Modifier.padding(top = 8.dp).background(Color.LightGray.copy(alpha = 0.3f), shape = CircleShape)) {
                        Icon(painter = painterResource(R.drawable.ic_review),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(40.dp).padding(4.dp)
                                .clickable {
                                    navController.navigate("review" + "/${movie!!.id}")
                                })
                    }
                }

            }

        }
    }
}