package com.rodrigoloq.moviepedia.search

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.rodrigoloq.moviepedia.R
import com.rodrigoloq.moviepedia.retrofit.entities.Result
import com.rodrigoloq.moviepedia.ui.theme.MoviepediaTheme

@Preview
@Composable
fun SearchResultItemViewPreview(){
    MoviepediaTheme() {
        SearchResultItemView(movie = null)
    }
}

@Composable
fun SearchResultItemView(modifier: Modifier = Modifier, movie: Result?){


    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https://image.tmdb.org/t/p/original" + movie?.poster_path)
            .crossfade(true)
            .build(),
        contentDescription = null,
        placeholder = painterResource(R.drawable.poster_loading),
        error = painterResource(R.drawable.no_image),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
        modifier = modifier
            .width(130.dp)
            .height(195.dp)
    )
}