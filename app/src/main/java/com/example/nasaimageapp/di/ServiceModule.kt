package com.example.nasaimageapp.di

import com.example.nasaimageapp.networking.NasaImageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideNasaService(
        retrofit: Retrofit,
    ): NasaImageService = retrofit.create(NasaImageService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(
        moshi: MoshiConverterFactory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://images-api.nasa.gov/")
        .addConverterFactory(moshi)
        .build()

    @Singleton
    @Provides
    fun provideMoshi(): MoshiConverterFactory = MoshiConverterFactory.create()
}