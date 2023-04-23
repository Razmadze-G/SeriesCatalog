package com.razmadze.tvseriescatalog.models

import com.google.gson.annotations.SerializedName

data class PopularSeries(
    @SerializedName("page")
    val pageNumber: Int,
    @SerializedName("results")
    val seriesList: List<Series>,
    @SerializedName("total_pages")
    val totalPageCount: Int,
    @SerializedName("total_results")
    val totalSeriesCount: Int
)