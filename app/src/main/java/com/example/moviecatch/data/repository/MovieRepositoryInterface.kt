package com.example.moviecatch.data.repository

import androidx.paging.PagingData
import com.example.moviecatch.data.model.BaseModel
import com.example.moviecatch.data.model.Genres
import com.example.moviecatch.data.model.MovieItem
import com.example.moviecatch.data.model.artist.Artist
import com.example.moviecatch.data.model.artist.ArtistDetail
import com.example.moviecatch.data.model.moviedetail.MovieDetail
import com.example.moviecatch.utils.DataState
import kotlinx.coroutines.flow.Flow

interface MovieRepositoryInterface {

    suspend fun movieDetail(movieId: Int): Flow<DataState<MovieDetail>>
    suspend fun recommendedMovie(movieId: Int, page: Int): Flow<DataState<BaseModel>>
    suspend fun search(searchKey: String): Flow<DataState<BaseModel>>
    suspend fun genreList(): Flow<DataState<Genres>>
    suspend fun movieCredit(movieId: Int): Flow<DataState<Artist>>
    suspend fun artistDetail(personId: Int): Flow<DataState<ArtistDetail>>
    fun nowPlayingPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun popularPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun topRatedPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun upcomingPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
    fun genrePagingDataSource(genreId: String): Flow<PagingData<MovieItem>>

}