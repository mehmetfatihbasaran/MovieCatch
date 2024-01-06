package com.example.moviecatch.di

import com.example.moviecatch.data.ApiService
import com.example.moviecatch.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

object RepositoryModule {

    @Module
    @InstallIn(SingletonComponent::class)
    object RepositoryModule {
        /**
         * Provides RemoteDataRepository for access api service method
         */
        @Singleton
        @Provides
        fun provideMovieRepository(
            apiService: ApiService,
        ): MovieRepository {
            return MovieRepository(
                apiService
            )
        }

    }
}