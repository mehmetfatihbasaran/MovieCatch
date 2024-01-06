package com.example.moviecatch.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moviecatch.R
import com.example.moviecatch.data.ApiURL
import com.example.moviecatch.data.model.BaseModel
import com.example.moviecatch.navigation.Screen
import com.example.moviecatch.utils.DataState
import com.example.moviecatch.utils.roundTo
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent

@Composable
fun SearchUI(navController:NavController, searchData: MutableState<DataState<BaseModel>?>, itemClick:()->Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(0.dp, 350.dp) // define max height
            .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
            .padding(top = 8.dp)

    ) {
        searchData.value?.let {
            if (it is DataState.Success<BaseModel>) {
                items(items = it.data.results, itemContent = { item ->
                    Row(modifier = Modifier
                        .padding(bottom = 8.dp, start = 8.dp , end = 8.dp)
                        .clickable {
                            itemClick.invoke()
                            navController.navigate(
                                Screen.MovieDetail.route.plus(
                                    "/${item.id}"
                                )
                            )
                        }) {
                        CoilImage(
                            modifier = Modifier
                                .height(100.dp)
                                .width(80.dp),
                            imageModel = {  ApiURL.IMAGE_URL.plus(item.backdropPath) },
                            imageOptions = ImageOptions(
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center,
                                contentDescription = "search item",
                                colorFilter = null,
                            ),
                            component = rememberImageComponent {
                                +CircularRevealPlugin(
                                    duration = 800
                                )
                            },
                        )
                        Column {
                            Text(
                                text = item.title,
                                modifier = Modifier.padding(
                                    start = 8.dp,
                                    top = 4.dp
                                ),
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = item.releaseDate,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                            Text(
                                text = "${stringResource(R.string.rating_search)} ${item.voteAverage.roundTo(1)}",
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                })
            }
        }
    }
}