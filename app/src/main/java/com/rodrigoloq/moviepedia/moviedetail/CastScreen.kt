package com.rodrigoloq.moviepedia.moviedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.rodrigoloq.moviepedia.retrofit.entities.Cast
import com.rodrigoloq.moviepedia.retrofit.entities.CreditsResponse
import com.rodrigoloq.moviepedia.ui.theme.MoviepediaTheme


@Preview
@Composable
fun CastViewPreview(){
    MoviepediaTheme() {
        CastView(Modifier, Cast(name = "asdfjkasd asdfasdasdfasdf", character = "sadfklasdjf"))
    }
}



@Composable
fun CastView(modifier: Modifier = Modifier, cast: Cast){
    Column(modifier
        .background(Color.Transparent).width(138.dp).padding(end = 8.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/original" + cast.profile_path)
                .crossfade(true)
                .build(),
            contentDescription = null,
            placeholder = painterResource(R.drawable.poster_loading),
            error = painterResource(R.drawable.no_poster),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .width(130.dp)
                .height(195.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Text(cast.name, fontSize = 12.sp, fontWeight = FontWeight.Bold,
            color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(cast.character, fontSize = 12.sp, color = Color.Gray)
    }
}