package com.rodrigoloq.moviepedia.reviews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.rodrigoloq.moviepedia.R
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.reviews.viewmodel.ReviewsViewModel
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.entities.Reviews
import com.rodrigoloq.moviepedia.room.entities.User
import com.rodrigoloq.moviepedia.ui.theme.MoviepediaTheme
import com.rodrigoloq.moviepedia.utils.Utils

@Preview(showBackground = true)
@Composable
fun ReviewItemViewPreview(){
    MoviepediaTheme() {
        ReviewItemView(userReview = mapOf(), review = Reviews(
            rating = 1,
            userId = 1,
            movieId = 1,
            review = "TODO()"
        )){}
    }
}

@Composable
fun ReviewItemView(modifier: Modifier = Modifier,
                   review: Reviews,
                   userReview: Map<Reviews,User>,
                   showBottomSheet:() -> Unit){

    val user = userReview[review]


    val date = if(review.editedAt.toInt() != 0){
        Utils().dateFormater(review.editedAt, false)
    } else { Utils().dateFormater(review.postedAt, false) }

    Column() {
        Row(modifier.padding(8.dp).fillMaxWidth()) {
            Box(Modifier.padding(4.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user?.imgLocation ?: "")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.perfil_user),
                    error = painterResource(R.drawable.perfil_user),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(45.dp)
                )
            }
            Column(Modifier.padding(start = 8.dp).weight(1f)) {
                Row(Modifier.padding(bottom = 8.dp)) {
                    Text(user?.username ?: "Usuario", Modifier.padding(end = 8.dp))
                    for (i in 1..review.rating){
                        Text("⭐")
                    }
                }
                Text(review.review,
                    Modifier.padding(bottom = 8.dp))
                Text(date,
                    Modifier.fillMaxWidth(), textAlign = TextAlign.End)
            }
            if (review.userId == MoviepediaApp.auth.id){
                Icon(painter = painterResource(R.drawable.ic_menu),
                    contentDescription = "",
                    tint = Color.Gray,
                    modifier = Modifier.size(30.dp)
                        .clickable {
                            showBottomSheet()
                        })
            }
        }
        HorizontalDivider()
    }

}