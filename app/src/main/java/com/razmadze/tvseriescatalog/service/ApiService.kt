package com.razmadze.tvseriescatalog.service

import com.razmadze.tvseriescatalog.models.PopularSeries
import com.razmadze.tvseriescatalog.models.SeriesDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("tv/popular")
    suspend fun getPopularSeriesList(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): PopularSeries

    @GET("search/tv")
    suspend fun getSearchSeriesList(
        @Query("api_key") apiKey: String,
        @Query("query") searchKeyWord: String,
        @Query("page") pageNumber: Int
    ): PopularSeries

    @GET("tv/{tv_id}/similar")
    suspend fun getSimilarSeriesList(
        @Path("tv_id") seriesID: Int,
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int
    ): PopularSeries

    @GET("tv/{tv_id}")
    suspend fun getSeriesDetails(
        @Path("tv_id") seriesID: Int,
        @Query("api_key") apiKey: String,
    ): SeriesDetails

}