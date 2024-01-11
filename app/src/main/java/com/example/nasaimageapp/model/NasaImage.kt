package com.example.nasaimageapp.model

import com.squareup.moshi.Json

data class NasaImage(
    val id: String,
    val title: String,
    val description: String,
    val photographer: String,
    val location: String,
    val url: String,
)

data class NetworkImageData(
    @Json(name = "nasa_id")
    val nasa_id: String, // tried renaming to "id" but there seems to be an issue
    val title: String?,
    val description: String?,
    val photographer: String?,
    val location: String?,
)

data class NasaImageLinkObject(
    @Json(name = "href")
    val href: String?, // tried changing the name to "url" but it's not working. Curious what I'm doing wrong?
)

data class NasaItemObject(
    val data: List<NetworkImageData>,
    val links: List<NasaImageLinkObject>
)

data class Collection(
    val items: List<NasaItemObject>
)

data class NasaResponse(
    val collection: Collection
)

fun List<NasaItemObject>.toNasaImageDataList(): List<NasaImage> {
    val nasaImageDataList = mutableListOf<NasaImage>()
    this.forEach { nasaItemObject ->
        nasaItemObject.links.first().href?.let { url ->
            nasaImageDataList.add(
                NasaImage(
                    id = nasaItemObject.data.first().nasa_id,
                    title = nasaItemObject.data.first().title ?: "no title found",
                    description = nasaItemObject.data.first().description ?: "no description found",
                    photographer = nasaItemObject.data.first().photographer ?: "no photographer found",
                    location = nasaItemObject.data.first().location ?: "no location found",
                    url = url,
                )
            )
        }
    }
    return nasaImageDataList.toList()
}
