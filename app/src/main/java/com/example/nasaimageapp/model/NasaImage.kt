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
    @field:Json(name = "nasa_id")
    val nasaId: String,
    val title: String?,
    val description: String?,
    val photographer: String?,
    val location: String?,
)

data class NasaImageLinkObject(
    @field:Json(name = "href")
    val imgUrl: String?,
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

fun NasaItemObject.toNasaImageData(): NasaImage? {
    return this.links.first().imgUrl?.let {
        NasaImage(
            id = this.data.first().nasaId,
            title = this.data.first().title ?: "no title found",
            description = this.data.first().description ?: "no description found",
            photographer = this.data.first().photographer ?: "no photographer found",
            location = this.data.first().location ?: "no location found",
            url = it
        )
    }
}
