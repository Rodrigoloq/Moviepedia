package com.rodrigoloq.moviepedia.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.rodrigoloq.moviepedia.R
import com.rodrigoloq.moviepedia.retrofit.entities.GenreMapper
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.ui.theme.MoviepediaTheme

@Preview(showBackground = true)
@Composable
fun FavoriteMoviesItemViewPreview(){
    MoviepediaTheme() {
        FavoriteMoviesItemView(movie = null){ boolean, string ->

        }
    }
}

@Composable
fun FavoriteMoviesItemView(modifier: Modifier = Modifier, movie: MovieDetailResponse?, showBottomSheet:(Boolean, MovieDetailResponse) -> Unit){

    val genresList = movie?.genres ?: listOf()
    val genresIdList = genresList.map { it.id }
    val genres = GenreMapper.getGenreNames(genresIdList)

    Column(modifier.background(Color.Transparent).padding(vertical = 2.dp)) {
        Row(Modifier.fillMaxWidth().height(150.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/original" + movie?.poster_path)
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
            Column(modifier = Modifier.padding(start = 8.dp).weight(1f)) {
                Text(text = movie?.title ?: "-",
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis)
                Text(text = "⭐ ${movie?.vote_average ?: "-"}",
                    fontSize = 10.sp)
                Text(movie?.release_date?.take(4) ?: "-",
                    fontSize = 10.sp)
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    genres.take(3).forEach {genre ->
                        Button(onClick = {}, enabled = false,
                            contentPadding = PaddingValues(8.dp),
                            modifier = Modifier.padding(end = 4.dp)) {
                            Text(text = genre, fontSize = 10.sp)
                        }
                    }
                }
            }
            Box(modifier = Modifier.fillMaxHeight().padding(top = 4.dp)){
                Icon(painter = painterResource(R.drawable.ic_menu),
                    contentDescription = "",
                    tint = Color.Gray,
                    modifier = Modifier.size(40.dp)
                        .clickable {
                            showBottomSheet(true, movie!!)
                        })
            }
        }
        HorizontalDivider()
    }
}