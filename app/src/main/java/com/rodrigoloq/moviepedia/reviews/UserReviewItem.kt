package com.rodrigoloq.moviepedia.reviews

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.rodrigoloq.moviepedia.R
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.room.entities.Reviews
import com.rodrigoloq.moviepedia.ui.theme.MoviepediaTheme
import com.rodrigoloq.moviepedia.utils.Utils
import kotlin.math.sin

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UserReviewItemPreview(){
    MoviepediaTheme() {
        UserReviewItemView(Modifier, review = Reviews(
            rating = 1,
            userId = 1,
            movieId = 1,
            review = "Marty is a rising ping pong player who becomes obsessed with his own success and proving himself to everyone around him. The movie is chaotic in a humorous way, but also includes a lot of drama which contributes to the plot. The mix between these emotions throughout the movie kind of catches you off guard, therefore making the movie more interesting.  Timothee Chalamet does a really good job at making Marty both unlikeable and entertaining at the same time, which is probably why the movie has done so well. He really embodies the mindset of personal freedom, as he does everything HE wants to do, not what everybody wants him to do. Some scenes in the movie feel unnecessarily long or weird (like the falling through ceiling scene) and there are moments where the story loses focus, but the energy throughout the duration of the movie keeps it interesting. If you like fast-paced movies that seem bland, but do have a real main idea/takeaway, this is a great movie for you to watch. Overall, a good movie and another example of how versatile Timothee Chalamet is as an actor."
        ), mapOf()){movie, string, i ->

        }
    }
}

@Composable
fun UserReviewItemView(modifier: Modifier = Modifier,
                       review: Reviews,
                       movieReview: Map<Reviews, MovieDetailResponse>,
                       showBottomSheet:(MovieDetailResponse, String, Int) -> Unit){

    val movie = movieReview[review]

    val date = if(review.editedAt.toInt() != 0){
        Utils().dateFormater(review.editedAt, false)
    } else { Utils().dateFormater(review.postedAt, false) }

    Row(modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/original" + "${movie?.poster_path}")
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
        Column(modifier = Modifier.padding(start = 8.dp).height(150.dp).weight(1f)) {
            Text(text = "${movie?.release_date?.take(4) ?: 1960} - ${movie?.title ?: "-"}",
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
            Text(review.review,
                fontSize = 14.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis)
            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End) {
                Text(date,
                    fontSize = 14.sp)
            }

        }
        Icon(painter = painterResource(R.drawable.ic_menu),
            contentDescription = "",
            tint = Color.Gray,
            modifier = Modifier.size(30.dp).padding(top = 4.dp)
                .clickable {
                    showBottomSheet(movie!!,review.review,review.rating)
                })
    }
    HorizontalDivider()
}