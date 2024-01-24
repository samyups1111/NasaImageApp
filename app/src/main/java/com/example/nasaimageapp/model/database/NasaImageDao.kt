package com.example.nasaimageapp.model.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nasaimageapp.model.NasaImage

@Dao
interface NasaImageDao {

    @Query("SELECT * FROM nasaImage WHERE title LIKE '%' || :query || '%'")
    fun getPagedList(query: String): PagingSource<Int, NasaImage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<NasaImage>)

    @Query("DELETE FROM nasaImage WHERE title LIKE '%' || :query || '%'")
    suspend fun deleteByQuery(query: String)
}