package com.example.nasaimageapp.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nasaimageapp.model.NasaImage
import com.example.nasaimageapp.networking.RemoteKey

@Database(entities = [NasaImage::class, RemoteKey::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun nasaImageDao(): NasaImageDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}