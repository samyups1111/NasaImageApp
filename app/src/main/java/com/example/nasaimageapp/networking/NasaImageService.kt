package com.example.nasaimageapp.networking

import com.example.nasaimageapp.model.NasaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaImageService {
    @GET("search")
    suspend fun getNasaImages(
        @Query("title") title: String = "nasa",
        @Query("media_type") mediaType: String = "image",
    ): Response<NasaResponse>
}