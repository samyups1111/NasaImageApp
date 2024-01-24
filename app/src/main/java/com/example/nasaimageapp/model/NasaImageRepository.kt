package com.example.nasaimageapp.model
interface NasaImageRepository {
    suspend fun getNasaImages(query: String, page: Int): List<NasaImage>
}