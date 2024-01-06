package com.example.moviecatch.ui.screens.bottomnavigation.toprated

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.moviecatch.data.model.GenreId
import com.example.moviecatch.data.model.moviedetail.Genre
import com.example.moviecatch.data.repository.MovieRepository
import com.example.moviecatch.utils.AppConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class TopRatedViewModel @Inject constructor(val repo: MovieRepository) : ViewModel() {
    var selectedGenre: MutableState<Genre> =
        mutableStateOf(Genre(null, AppConstant.DEFAULT_GENRE_ITEM))
    val filterData = MutableStateFlow<GenreId?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val topRatedMovies = filterData.flatMapLatest {
        repo.topRatedPagingDataSource(it?.genreId)
    }.cachedIn(viewModelScope)
}