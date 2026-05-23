package com.rodrigoloq.moviepedia.home

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.CarouselState
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
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
import com.rodrigoloq.moviepedia.home.viewmodel.HomeViewModel
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.ui.theme.MoviepediaTheme

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeViewPreview(){
    MoviepediaTheme() {
        Scaffold() {
            HomeView(rememberNavController(),Modifier.padding(it))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController,
             modifier: Modifier = Modifier,
             viewModel: HomeViewModel = viewModel()){

    val lastMovies = viewModel.lastMovies
    val popularMovies = viewModel.popularMovies
    val userData = viewModel.userData
    val isLoading = viewModel.isLoading

    LaunchedEffect(Unit) {
        viewModel.getListLastMovies()
        viewModel.getListPopularMovies()
        viewModel.getUserInfo()
    }

    val carouselState = remember(lastMovies) {
        CarouselState(itemCount = { lastMovies.count() })
    }

    Column(modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)) {
            Box(modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterVertically)){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(userData?.imgLocation ?: "")
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
            Column() {
                Text(text = "Bienvenido a Moviepedia \uD83D\uDC4B",
                    fontSize = 16.sp)
                Text(text = userData?.username ?: "-",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp)
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Ultimos lanzamientos",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp)
        }
        if (!isLoading){
            HorizontalMultiBrowseCarousel(
                state = carouselState,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                itemSpacing = 8.dp,
                preferredItemWidth = 130.dp
            ) {i ->
                val movie = lastMovies[i]
                LastMoviesItemView(modifier = Modifier.clickable{
                    navController.navigate("detail" + "/${movie.id}")
                },movie = movie)
            }
        } else {
            Column(Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
                Text(text = "Cargando peliculas...")
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Popular",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp)
        }
        if(!isLoading){
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)) {
                items(popularMovies.size){i ->
                    val movie = popularMovies[i]
                    PopularMoviesItemView(modifier = Modifier.clickable{
                        navController.navigate("detail" + "/${movie.id}")
                    },movie = movie)
                }
            }
        } else {
            Column(Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
                Text(text = "Cargando peliculas...")
            }
        }
    }
}