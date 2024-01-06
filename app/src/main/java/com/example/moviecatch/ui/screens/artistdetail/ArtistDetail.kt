package com.example.moviecatch.ui.screens.artistdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviecatch.data.ApiURL
import com.example.moviecatch.data.model.artist.ArtistDetail
import com.example.moviecatch.ui.component.CircularIndeterminateProgressBar
import com.example.moviecatch.ui.component.text.BioGraphyText
import com.example.moviecatch.utils.DataState
import com.example.moviecatch.utils.genderInString
import com.example.moviecatch.utils.pagingLoadingState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent

@Composable
fun ArtistDetail(personId: Int) {
    val artistDetailViewModel = hiltViewModel<ArtistDetailViewModel>()
    val artistDetail = artistDetailViewModel.artistDetail
    val progressBar = remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        artistDetailViewModel.artistDetail(personId)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                color = MaterialTheme.colors.background
            )
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
    ) {
        CircularIndeterminateProgressBar(isDisplayed = progressBar.value, 0.4f)

        artistDetail.value.let {
            if (it is DataState.Success<ArtistDetail>) {
                Row {
                    CoilImage(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .height(250.dp)
                            .width(190.dp),
                        imageModel = { ApiURL.IMAGE_URL.plus(it.data.profilePath) },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                            contentDescription = "artist image",
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
                            modifier = Modifier.padding(start = 8.dp),
                            text = it.data.name,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Medium
                        )
                        PersonalInfo(
                            stringResource(com.example.moviecatch.R.string.know_for),
                            it.data.knownForDepartment
                        )
                        PersonalInfo(
                            stringResource(com.example.moviecatch.R.string.gender),
                            it.data.gender.genderInString()
                        )
                        it.data.birthday?.let { birthday ->
                            PersonalInfo(
                                stringResource(com.example.moviecatch.R.string.birth_day),
                                birthday
                            )
                        }
                        it.data.placeOfBirth?.let { birthPlace ->
                            PersonalInfo(
                                stringResource(com.example.moviecatch.R.string.place_of_birth),
                                birthPlace
                            )
                        }
                    }
                }
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = stringResource(com.example.moviecatch.R.string.biography),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                )
                BioGraphyText(
                    text = it.data.biography
                )
            }
        }
    }

    artistDetail.pagingLoadingState {
        progressBar.value = it
    }
}

@Composable
fun PersonalInfo(title: String, info: String) {
    Column(modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)) {
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = info, fontSize = 16.sp
        )
    }
}