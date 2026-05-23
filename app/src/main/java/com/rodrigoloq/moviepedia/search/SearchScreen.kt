package com.rodrigoloq.moviepedia.search

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.rodrigoloq.moviepedia.R
import com.rodrigoloq.moviepedia.home.PopularMoviesItemView
import com.rodrigoloq.moviepedia.home.viewmodel.HomeViewModel
import com.rodrigoloq.moviepedia.retrofit.entities.Result
import com.rodrigoloq.moviepedia.search.viewmodel.SearchViewModel
import com.rodrigoloq.moviepedia.ui.theme.MoviepediaTheme

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchViewPreview(){
    MoviepediaTheme() {
        Scaffold() {
            SearchView(rememberNavController(),Modifier.padding(it))
        }
    }
}

@Composable
fun SearchView(navController: NavController,
               modifier: Modifier = Modifier,
               viewModel: SearchViewModel = viewModel()){
    val context = LocalContext.current
    val isLoading = viewModel.isLoading
    val searchResults = viewModel.searchResults
    val topRatedMovies = viewModel.topRatedMovies

    var searchTextValue by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getListTopRatedMovies()
    }

    Column(modifier.fillMaxSize()) {
        TextField(
            value = searchTextValue,
            onValueChange = { searchTextValue = it
                            viewModel.getListSearchResult(it)},
            leadingIcon = {
                Icon(Icons.Default.Search,"")
            },
            trailingIcon = {
                IconButton(onClick = {searchTextValue = ""}) {
                    if (!searchTextValue.trim().isEmpty())
                    Icon(Icons.Default.Close, "")
                }
            },
            modifier = Modifier
                .padding(end = 5.dp)
                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    drawLine(
                        color = Color.DarkGray,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidth
                    )
                    drawLine(
                        color = Color.DarkGray,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = strokeWidth
                    )
                },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.DarkGray, // Ocultar borde por defecto
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        if(searchTextValue.trim().isEmpty()){
            if(!isLoading){
                LazyVerticalGrid(columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize()) {
                    items(topRatedMovies.size){ i ->
                        val movie = topRatedMovies[i]
                        SearchResultItemView(movie = movie,
                            modifier = Modifier.clickable{
                                navController.navigate("detail" + "/${movie.id}")
                        })
                    }
                }
            }else{
                Column(Modifier.fillMaxSize().padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    CircularProgressIndicator()
                    Text(text = "Cargando peliculas...")
                }
            }
        } else {
            if(searchResults.isEmpty() && !isLoading){
                Column(modifier = Modifier.fillMaxSize().padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Image(painter = painterResource(R.drawable.not_found),
                        null,
                        modifier = Modifier.size(64.dp))
                    Text("No se encontraron resultados")
                }
            }else{
                LazyVerticalGrid(columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize()) {
                    items(searchResults.size){ i ->
                        val movie = searchResults[i]
                        SearchResultItemView(movie = movie,
                            modifier = Modifier.clickable{
                                navController.navigate("detail" + "/${movie.id}")
                        })
                    }
                }
            }
        }
    }
}