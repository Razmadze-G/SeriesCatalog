package com.razmadze.tvseriescatalog.ui.detailScreen

import androidx.lifecycle.ViewModel
import com.razmadze.tvseriescatalog.models.Series
import com.razmadze.tvseriescatalog.repository.SeriesRepository
import com.razmadze.tvseriescatalog.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: SeriesRepository): ViewModel() {

    suspend fun getSeries(seriesId: Int): Resource<Series> {
        return repository.getSeriesDetails(seriesId)
    }
}