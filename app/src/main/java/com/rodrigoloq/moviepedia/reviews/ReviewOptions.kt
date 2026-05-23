package com.rodrigoloq.moviepedia.reviews

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rodrigoloq.moviepedia.R
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.room.MoviepediaApp

@Composable
fun ReviewOptionsView(modifier: Modifier = Modifier,
                      isUserReviews: Boolean = false,
                      navController: NavController = rememberNavController(),
                      movieId: Int = 0,
                      onDismiss:() -> Unit,
                      onShowReview:() -> Unit,
                      onDeleteReview:() -> Unit){

    Column(modifier.fillMaxWidth()) {
        Text("Opciones",
            Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            fontWeight = FontWeight.Bold)
        HorizontalDivider()
        if (isUserReviews){
            Row(Modifier.fillMaxWidth().clickable{
                onDismiss()
                navController.navigate("review" + "/${movieId}")
            }, verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.padding(start = 8.dp)) {
                    Icon(painter = painterResource(R.drawable.ic_details), "")
                }
                Text("Ver reseña", Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp))
            }
        }
        Row(Modifier.fillMaxWidth().clickable{
            onShowReview()
            onDismiss()
        }, verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.padding(start = 8.dp)) {
                Icon(painter = painterResource(R.drawable.ic_edit_review), "")
            }
            Text("Editar reseña", Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp))
        }
        Row(Modifier.fillMaxWidth().clickable{
            onDeleteReview()
            onDismiss()
        }, verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.padding(start = 8.dp)) {
                Icon(painter = painterResource(R.drawable.ic_delete_review), "")
            }
            Text("Eliminar reseña", Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp))
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