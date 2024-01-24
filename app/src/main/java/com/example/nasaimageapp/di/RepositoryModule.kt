package com.example.nasaimageapp.di

import com.example.nasaimageapp.model.NasaImageRepository
import com.example.nasaimageapp.model.NasaImageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindNasaImageRepositoryImpl(
        repository: NasaImageRepositoryImpl,
    ): NasaImageRepository
}