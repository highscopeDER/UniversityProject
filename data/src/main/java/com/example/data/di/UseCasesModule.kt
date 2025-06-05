package com.example.data.di

import com.example.domain.repositories.ApiRepository
import com.example.domain.repositories.PathFindRepository
import com.example.domain.usecases.LoadUseCase
import com.example.domain.usecases.RequestRouteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @Provides
    fun provideLoadUseCase(apiRepository: ApiRepository, pathFindRepository: PathFindRepository): LoadUseCase {
        return LoadUseCase(apiRepository, pathFindRepository)
    }

    @Provides
    fun provideRequestRouteUseCase(repository: PathFindRepository): RequestRouteUseCase {
        return RequestRouteUseCase(repository)
    }

}


