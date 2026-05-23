package com.rodrigoloq.moviepedia.moviedetail

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodrigoloq.moviepedia.retrofit.entities.AlternativeTitles
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.retrofit.entities.ProductionCompanies
import com.rodrigoloq.moviepedia.retrofit.entities.SpokenLanguages
import com.rodrigoloq.moviepedia.ui.theme.MoviepediaTheme

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailViewPreview(){
    MoviepediaTheme() {
        DetailView(modifier = Modifier, MovieDetailResponse(production_companies = listOf(
            ProductionCompanies(0,"","A24","as"),
            ProductionCompanies(0,"","A24","as")),
            origin_country = listOf("Estados Unidos", "Mexico"),
            spoken_languages = listOf(SpokenLanguages("asdf","12312","Ingles"),
                SpokenLanguages("asdf","12312","Español"))),
            altTitles = listOf(AlternativeTitles("","SAdfsdf"), AlternativeTitles("","pSAdsfsdf")))
    }
}

@Composable
fun DetailView(modifier: Modifier = Modifier,
               movie: MovieDetailResponse?,
               altTitles: List<AlternativeTitles>){
    Column(modifier
        .background(Color.Transparent)
        .fillMaxWidth()) {
        Row(Modifier.padding(bottom = 8.dp)) {
            Row(Modifier
                .width(200.dp)
                .padding(end = 16.dp)) {
                Text("Productoras", color = Color.Gray, fontWeight = FontWeight.Light)
                HorizontalDivider(Modifier.align(Alignment.Bottom), color = Color.Gray, thickness = (0.5).dp)
            }
            FlowRow() {
                movie?.production_companies?.forEach {
                    Box(Modifier
                        .padding(end = 4.dp, bottom = 4.dp)
                        .clip(RoundedCornerShape(4.dp))){
                        Text(it.name, Modifier
                            .background(Color.LightGray)
                            .padding(horizontal = 6.dp, vertical = 3.dp), color = Color.DarkGray)
                    }
                }
            }
        }
        Row(Modifier.padding(bottom = 8.dp)) {
            Row(Modifier
                .width(200.dp)
                .padding(end = 16.dp)) {
                Text("Pais", color = Color.Gray, fontWeight = FontWeight.Light)
                HorizontalDivider(Modifier.align(Alignment.Bottom), color = Color.Gray, thickness = (0.5).dp)
            }
            FlowRow() {
                movie?.origin_country?.forEach {
                    Box(Modifier
                        .padding(end = 4.dp, bottom = 4.dp)
                        .clip(RoundedCornerShape(4.dp))){
                        Text(it, Modifier
                            .background(Color.LightGray)
                            .padding(horizontal = 6.dp, vertical = 3.dp), color = Color.DarkGray)
                    }
                }
            }
        }
        Row(Modifier.padding(bottom = 8.dp)) {
            Row(Modifier
                .width(200.dp)
                .padding(end = 16.dp)) {
                Text("Idiomas", color = Color.Gray, fontWeight = FontWeight.Light)
                HorizontalDivider(Modifier.align(Alignment.Bottom), color = Color.Gray, thickness = (0.5).dp)
            }
            FlowRow() {
                movie?.spoken_languages?.forEach {
                    Box(Modifier
                        .padding(end = 4.dp, bottom = 4.dp)
                        .clip(RoundedCornerShape(4.dp))){
                        Text(it.name, Modifier
                            .background(Color.LightGray)
                            .padding(horizontal = 6.dp, vertical = 3.dp), color = Color.DarkGray)
                    }
                }
            }
        }
        Row(Modifier.padding(bottom = 8.dp)) {
            Row(Modifier
                .width(200.dp)
                .padding(end = 16.dp)) {
                Text("Duracion", color = Color.Gray, fontWeight = FontWeight.Light)
                HorizontalDivider(Modifier.align(Alignment.Bottom), color = Color.Gray, thickness = (0.5).dp)
            }
            FlowRow() {
                Box(Modifier
                    .padding(end = 4.dp, bottom = 4.dp)
                    .clip(RoundedCornerShape(4.dp))){
                    Text(movie?.runtime.toString() + " minutos", Modifier
                        .background(Color.LightGray)
                        .padding(horizontal = 6.dp, vertical = 3.dp), color = Color.DarkGray)
                }
            }
        }
        Row(Modifier.padding(bottom = 8.dp)) {
            Row(Modifier
                .width(200.dp)
                .padding(end = 16.dp)) {
                Text("Presupuesto", color = Color.Gray, fontWeight = FontWeight.Light)
                HorizontalDivider(Modifier.align(Alignment.Bottom), color = Color.Gray, thickness = (0.5).dp)
            }
            FlowRow() {
                Box(Modifier
                    .padding(end = 4.dp, bottom = 4.dp)
                    .clip(RoundedCornerShape(4.dp))){
                    Text("$ " + movie?.budget.toString(), Modifier
                        .background(Color.LightGray)
                        .padding(horizontal = 6.dp, vertical = 3.dp), color = Color.DarkGray)
                }
            }
        }
        Row(Modifier.padding(bottom = 8.dp)) {
            Row(Modifier
                .width(200.dp)
                .padding(end = 16.dp)) {
                Text("Recaudacion", color = Color.Gray, fontWeight = FontWeight.Light)
                HorizontalDivider(Modifier.align(Alignment.Bottom), color = Color.Gray, thickness = (0.5).dp)
            }
            FlowRow() {
                Box(Modifier
                    .padding(end = 4.dp, bottom = 4.dp)
                    .clip(RoundedCornerShape(4.dp))){
                    Text("$ " + movie?.revenue.toString(), Modifier
                        .background(Color.LightGray)
                        .padding(horizontal = 6.dp, vertical = 3.dp), color = Color.DarkGray)
                }
            }
        }
        Row() {
            Row(Modifier
                .width(200.dp)
                .padding(end = 16.dp)) {
                Text("Titulos alternativos", color = Color.Gray, fontWeight = FontWeight.Light)
                HorizontalDivider(Modifier.align(Alignment.Bottom), color = Color.Gray, thickness = (0.5).dp)
            }
            FlowRow() {
                Text(
                    text = altTitles.joinToString(
                        separator = ", ",
                        postfix = "."
                    ) { it.title },
                    color = Color.Gray
                )
            }
        }
    }
}