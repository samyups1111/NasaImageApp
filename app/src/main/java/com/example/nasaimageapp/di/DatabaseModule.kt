package com.example.nasaimageapp.di

import android.content.Context
import androidx.room.Room
import com.example.nasaimageapp.model.database.AppDatabase
import com.example.nasaimageapp.model.database.NasaImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideNasaImageDao(appDatabase: AppDatabase): NasaImageDao {
        return appDatabase.nasaImageDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "NasaImageDatabase"
        ).build()
    }
}