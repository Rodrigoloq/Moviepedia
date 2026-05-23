package com.rodrigoloq.moviepedia.moviedetail

import android.graphics.Movie
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.rodrigoloq.moviepedia.R
import com.rodrigoloq.moviepedia.retrofit.entities.CountryMapper
import com.rodrigoloq.moviepedia.retrofit.entities.MovieRelease
import com.rodrigoloq.moviepedia.retrofit.entities.ReleaseDates
import com.rodrigoloq.moviepedia.ui.theme.MoviepediaTheme

@Preview(showBackground = true)
@Composable
fun ReleasesViewPreview() {
    MoviepediaTheme() {
        ReleasesView(
            releases = listOf(
                MovieRelease(
                    iso_3166_1 = "AR",
                    release_dates = listOf(
                        ReleaseDates(),
                        ReleaseDates(),
                        ReleaseDates()
                    )
                ),
                MovieRelease(
                    release_dates = listOf(
                        ReleaseDates(),
                        ReleaseDates(),
                        ReleaseDates()
                    )
                ),
                MovieRelease(release_dates = listOf(ReleaseDates(), ReleaseDates(), ReleaseDates()))
            )
        )
    }
}

@Composable
fun ReleasesView(modifier: Modifier = Modifier, releases: List<MovieRelease>) {

    val releaseType = mapOf(
        1 to "Premiere",
        2 to "Theatrical (limited)",
        3 to "Theatrical",
        4 to "Digital",
        5 to "Physical",
        6 to "TV"
    )

    Column(modifier.fillMaxWidth()) {
        releases.forEach { releases ->
            Column() {
                HorizontalDivider(Modifier.padding(bottom = 10.dp))
                Row(Modifier.padding(bottom = 10.dp)) {
                    Row(
                        modifier = Modifier.width(120.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://flagcdn.com/h20/${releases.iso_3166_1.lowercase()}.png")
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            placeholder = painterResource(R.drawable.flag),
                            error = painterResource(R.drawable.flag),
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                            modifier = Modifier
                                .clip(CircleShape)
                                .height(16.dp)
                                .width(16.dp)
                        )
                        Text("${CountryMapper.getCountryName(releases.iso_3166_1.lowercase())}",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 4.dp),
                            color = Color.Gray)
                    }
                    Column() {
                        releases.release_dates.forEach { releasesDates ->
                            Row() {
                                Text("${releasesDates.release_date.take(10)} • ",
                                    color = Color.Gray)
                                FlowRow() {
                                    Text("${releaseType[releasesDates.type]}",
                                        color = Color.Gray,
                                        modifier = Modifier.padding(end = 4.dp))
                                    if (!releasesDates.certification.trim().isEmpty()){
                                        Box(Modifier.padding(end = 4.dp)) {
                                            Text(
                                                releasesDates.certification, Modifier
                                                    .border((0.5).dp, Color.LightGray)
                                                    .padding(horizontal = 4.dp),
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Gray
                                            )
                                        }
                                    }
                                    Text(releasesDates.note,
                                        fontWeight = FontWeight.ExtraLight,
                                        color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}