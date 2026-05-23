package com.rodrigoloq.moviepedia.account

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.rodrigoloq.moviepedia.account.viewmodel.AccountViewModel
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.ui.theme.MoviepediaTheme
import com.rodrigoloq.moviepedia.utils.Utils

@Preview(showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,)
@Composable
fun AccountViewPreview(){
    MoviepediaTheme() {
        Scaffold() {
            AccountView(rememberNavController(),
                Modifier.padding(it))
        }
    }
}

@Composable
fun AccountView(navController: NavController,
                modifier: Modifier = Modifier,
                viewModel: AccountViewModel = viewModel()){

    LaunchedEffect(Unit) {
        viewModel.getUserInfo(MoviepediaApp.auth.id)
    }

    val isLoading = viewModel.isLoading
    val user = viewModel.userData

    var formatedDate by remember { mutableStateOf("") }
    formatedDate = Utils().dateFormater(user?.createdAt ?: 0, false)

    Column(modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Box(modifier = Modifier.padding(top = 20.dp)){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user?.imgLocation ?: "")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                placeholder = rememberVectorPainter(Icons.Default.HourglassEmpty),
                error = painterResource(R.drawable.perfil_user),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(120.dp)
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(modifier = Modifier.padding(top = 20.dp),
                text = "Nombres:",
                fontSize = 15.sp)
            Text(modifier = Modifier.padding(top = 20.dp),
                text = if (isLoading) "Cargando datos" else user?.username ?: "--",
                fontSize = 15.sp)
        }
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(modifier = Modifier.padding(top = 20.dp),
                text = "Email:",
                fontSize = 15.sp)
            Text(modifier = Modifier.padding(top = 20.dp),
                text = if (isLoading) "Cargando datos" else user?.email ?: "--",
                fontSize = 15.sp)
        }
        Row(modifier = Modifier
            .fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(modifier = Modifier.padding(top = 20.dp),
                text = "Miembro desde:",
                fontSize = 15.sp)
            Text(modifier = Modifier.padding(top = 20.dp),
                text = if (isLoading) "Cargando datos" else
                    if(formatedDate == "31/12/1969") "--" else formatedDate ,
                fontSize = 15.sp)
        }
        Button(modifier = Modifier.width(170.dp),
            onClick = {
                navController.navigate("user_reviews")
        }){
            Text("Mis reseñas")
        }
        Button(modifier = Modifier.width(170.dp).padding(vertical = 8.dp),
            onClick = {
                navController.navigate("edit_info")
        }){
            Text("Editar informacion")
        }
        Button(modifier = Modifier.width(170.dp),
            onClick = {
            viewModel.logout()
        }){
            Text("Cerrar sesion")
        }

    }

}