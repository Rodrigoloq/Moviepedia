package com.rodrigoloq.moviepedia.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.rodrigoloq.moviepedia.R
import com.rodrigoloq.moviepedia.retrofit.entities.GenreMapper
import com.rodrigoloq.moviepedia.retrofit.entities.Result
import com.rodrigoloq.moviepedia.ui.theme.MoviepediaTheme

@Preview(showBackground = true)
@Composable
fun PopularMoviesItemViewPreview(){
    MoviepediaTheme() {
        PopularMoviesItemView(movie = null)
    }
}

@Composable
fun PopularMoviesItemView(modifier: Modifier = Modifier, movie: Result?){

    val genres = GenreMapper.getGenreNames(movie?.genre_ids ?: listOf(10751,35,12,14))

    Row(modifier.background(Color.Transparent).fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically) {
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
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text = movie?.title ?: "-",
                fontWeight = FontWeight.Bold,
                maxLines = 1)
            Row(modifier = Modifier.width(130.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "⭐ ${movie?.vote_average ?: "-"}/5",
                    fontSize = 10.sp)
                Text("${movie?.vote_count ?: "-"} votos",
                    fontSize = 10.sp)
            }
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                genres.take(4).forEach {genre ->
                    Button(onClick = {}, enabled = false,
                        contentPadding = PaddingValues(8.dp),
                        modifier = Modifier.padding(end = 4.dp)) {
                        Text(text = genre, fontSize = 10.sp)
                    }
                }
            }
            Text("FECHA DE ESTRENO: ${movie?.release_date ?: "-"}",
                fontSize = 10.sp)
        }
    }

}