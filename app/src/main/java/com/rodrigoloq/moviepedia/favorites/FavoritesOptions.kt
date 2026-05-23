package com.rodrigoloq.moviepedia.favorites

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rodrigoloq.moviepedia.R
import com.rodrigoloq.moviepedia.favorites.viewmodel.FavoritesViewModel
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.room.MoviepediaApp

@Preview(showBackground = true)
@Composable
fun FavoritesOptionViewPreview(){
    FavoritesOptionsView(navController = rememberNavController(),
        movie = MovieDetailResponse(), onShowReview = {}, onDismiss = {})
}

@Composable
fun FavoritesOptionsView(modifier: Modifier = Modifier,
                         navController: NavController,
                         movie: MovieDetailResponse,
                         viewModel: FavoritesViewModel = viewModel(),
                         onDismiss:() -> Unit,
                         onShowReview:() -> Unit){
    val context = LocalContext.current
    val isMovieReviewed = viewModel.isMovieReviewed

    LaunchedEffect(Unit) {
        viewModel.isMovieReviewed(MoviepediaApp.auth.id, movie.id)
    }

    val shareMovie = {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://www.themoviedb.org/movie/${movie.id}")
            type = "text/plain"
        }
        val chooser = Intent.createChooser(intent, context.getString(R.string.app_name))
        context.startActivity(chooser)
    }


    Column(modifier.fillMaxWidth()) {
        Text("${movie.title} • ${movie.release_date.take(4)}",
            Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            fontWeight = FontWeight.Bold)
        HorizontalDivider()
        Row(Modifier.fillMaxWidth().clickable{
            viewModel.removeFavorite(MoviepediaApp.auth.id,movie.id){result ->
                if (result){
                    Toast.makeText(context,"Se elimino la pelicula de sus lista de favoritos",
                        Toast.LENGTH_SHORT).show()
                    onDismiss()
                }
            }
        }, verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.padding(start = 8.dp)) {
                Icon(painter = painterResource(R.drawable.ic_remove_favorite), "")
            }
            Text("Quitar de favoritos", Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp))
        }
        if(isMovieReviewed){
            Row(Modifier.fillMaxWidth().clickable{
                navController.navigate("review" + "/${movie.id}")
                onDismiss()
            }, verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.padding(start = 8.dp)) {
                    Icon(painter = painterResource(R.drawable.ic_review), "")
                }
                Text("Ver reseña", Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp))
            }
        } else {
            Row(Modifier.fillMaxWidth().clickable{
                onShowReview()
                onDismiss()
            }, verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.padding(start = 8.dp)) {
                    Icon(painter = painterResource(R.drawable.ic_review), "")
                }
                Text("Escribir una reseña", Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp))
            }
        }
        Row(Modifier.fillMaxWidth().clickable{
            navController.navigate("detail" + "/${movie.id}")
            onDismiss()
        }, verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.padding(start = 8.dp)) {
                Icon(painter = painterResource(R.drawable.ic_details), "")
            }
            Text("Ver detalles", Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp))
        }
        Row(Modifier.fillMaxWidth().clickable{
            shareMovie()
        }, verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.padding(start = 8.dp)) {
                Icon(painter = painterResource(R.drawable.ic_share), "")
            }
            Text("Compartir", Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp))
        }
        Row(Modifier.fillMaxWidth().clickable{
            onDismiss()
        }, verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.padding(start = 8.dp)) {
                Icon(painter = painterResource(R.drawable.ic_cancel), "")
            }
            Text("Cancelar", Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp))
        }
    }
}