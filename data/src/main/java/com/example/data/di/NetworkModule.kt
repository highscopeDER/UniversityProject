package com.example.data.di

import com.example.data.repositories.ApiRepositoryImpl
import com.example.data.repositories.PathFindRepositoryImpl
import com.example.domain.repositories.ApiRepository
import com.example.domain.repositories.PathFindRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideApiRepository(): ApiRepository = ApiRepositoryImpl()

    @Singleton
    @Provides
    fun providePathFindRepository(): PathFindRepository = PathFindRepositoryImpl()

}