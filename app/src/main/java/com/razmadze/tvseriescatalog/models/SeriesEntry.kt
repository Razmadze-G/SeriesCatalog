package com.razmadze.tvseriescatalog.models

import com.google.gson.annotations.SerializedName

data class SeriesEntry(
    val name: String,
    val id: Int,
    val overview: String,
    val voteAverage: Double,
    val firstAirDate: String?,
    val imagePath: String?,
)