package com.razmadze.tvseriescatalog.repository

import com.razmadze.tvseriescatalog.BuildConfig
import com.razmadze.tvseriescatalog.models.PopularSeries
import com.razmadze.tvseriescatalog.models.SeriesDetails
import com.razmadze.tvseriescatalog.service.ApiService
import com.razmadze.tvseriescatalog.utils.Resource
import com.razmadze.tvseriescatalog.utils.Resource.Success
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ActivityScoped
class SeriesRepository @Inject constructor(private val service: ApiService) {
    suspend fun getPopularSeriesList(pageNumber: Int): Resource<PopularSeries> {
        val response = try {
            service.getPopularSeriesList(BuildConfig.API_KEY, pageNumber)
        } catch (e: HttpException) {
            return Resource.Error(message = e.message ?: "Something went wrong")
        } catch (e: IOException) {
            return Resource.Error("Please check your network connection")
        } catch (e: Exception) {
            return Resource.Error(message = "Something went wrong")
        }

        return Success(response)
    }

    suspend fun getSimilarSeriesList(seriesId: Int, pageNumber: Int): Resource<PopularSeries> {
        val response = try {
            service.getSimilarSeriesList(seriesId, BuildConfig.API_KEY, pageNumber)
        } catch (e: HttpException) {
            return Resource.Error(message = e.message ?: "Something went wrong")
        } catch (e: IOException) {
            return Resource.Error("Please check your network connection")
        } catch (e: Exception) {
            return Resource.Error(message = "Something went wrong")
        }
        return Success(response)
    }

    suspend fun getSearchSeriesList(
        pageNumber: Int,
        searchKeyWord: String
    ): Resource<PopularSeries> {
        val response = try {
            service.getSearchSeriesList(BuildConfig.API_KEY, searchKeyWord, pageNumber)
        } catch (e: HttpException) {
            return Resource.Error(message = e.message ?: "Something went wrong")
        } catch (e: IOException) {
            return Resource.Error("Please check your network connection")
        } catch (e: Exception) {
            return Resource.Error(message = "Something went wrong")
        }

        return Success(response)
    }

    suspend fun getSeriesDetails(seriesId: Int): Resource<SeriesDetails> {
        val response = try {
            service.getSeriesDetails(seriesId, BuildConfig.API_KEY)
        } catch (e: HttpException) {
            return Resource.Error(message = e.message ?: "Something went wrong")
        } catch (e: IOException) {
            return Resource.Error("Please check your network connection")
        } catch (e: Exception) {
            return Resource.Error(message = "Something went wrong")
        }

        return Success(response)
    }
}