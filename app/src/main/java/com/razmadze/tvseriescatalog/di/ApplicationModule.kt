package com.razmadze.tvseriescatalog.di

import com.razmadze.tvseriescatalog.repository.SeriesRepository
import com.razmadze.tvseriescatalog.service.ApiService
import com.razmadze.tvseriescatalog.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideSeriesRepository(service: ApiService) = SeriesRepository(service)

    @Singleton
    @Provides
    fun providesApiService(): ApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiService::class.java)
    }
}