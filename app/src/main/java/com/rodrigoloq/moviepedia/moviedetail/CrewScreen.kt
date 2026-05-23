package com.rodrigoloq.moviepedia.moviedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rodrigoloq.moviepedia.retrofit.entities.Crew

@Composable
fun CrewView(modifier: Modifier = Modifier, crew: List<Crew>) {

    val groupedCrew: Map<String, List<Crew>> = crew.groupBy { it.department }

    val crewByDepartment: List<Pair<String, List<Crew>>> =
        groupedCrew.toList()

    var maxLines by remember { mutableIntStateOf(1) }

    crewByDepartment.forEach { (department, crewList) ->
        Column(
            modifier
                .background(Color.Transparent)
                .fillMaxWidth()
        ) {
            Row(Modifier.padding(bottom = 8.dp)) {
                Row(
                    Modifier
                        .width(200.dp)
                        .padding(end = 16.dp)
                ) {
                    Text(department, color = Color.Gray, fontWeight = FontWeight.Light)
                    HorizontalDivider(
                        Modifier.align(Alignment.Bottom),
                        color = Color.Gray,
                        thickness = (0.5).dp
                    )
                }
                FlowRow() {
                    crewList.forEach {
                        Box(
                            Modifier
                                .padding(end = 4.dp, bottom = 4.dp)
                                .clip(RoundedCornerShape(4.dp))
                        ) {
                            Text(
                                it.name,
                                Modifier
                                    .background(Color.LightGray)
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                                    .clickable{
                                        if (maxLines == 1) maxLines = 2 else maxLines = 1
                                    },
                                color = Color.DarkGray,
                                maxLines = maxLines,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}