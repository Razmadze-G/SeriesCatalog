package com.razmadze.tvseriescatalog.presentation.detailScreen.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razmadze.tvseriescatalog.models.SeriesDetails
import com.razmadze.tvseriescatalog.models.SeriesEntry
import com.razmadze.tvseriescatalog.presentation.detailScreen.state.DetailsStateModel
import com.razmadze.tvseriescatalog.repository.SeriesRepository
import com.razmadze.tvseriescatalog.utils.Constants
import com.razmadze.tvseriescatalog.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: SeriesRepository): ViewModel() {
    private val similarSeriesList = mutableListOf<SeriesEntry>()
    val state = MutableStateFlow(DetailsStateModel())

    suspend fun getSeriesDetails(seriesId: Int): Resource<SeriesDetails> {
        return repository.getSeriesDetails(seriesId)
    }

    fun loadPagination(id: Int) {
        viewModelScope.launch {
            state.update {
                it.copy(isLoading = true)
            }
            when (val result = repository.getSimilarSeriesList(seriesId = id, state.value.pageNumber)) {
                is Resource.Success -> {
                    val entries = result.data!!.seriesList.mapIndexed { _ , value ->
                        SeriesEntry(
                            imagePath = Constants.IMAGE_BASE_URL + value.posterPath,
                            name = value.name,
                            id = value.id,
                            overview = value.overview,
                            voteAverage = value.voteAverage,
                            firstAirDate = value.firstAirDate
                        )
                    }
                    similarSeriesList += entries
                    state.update {
                        it.copy(
                            similarSeriesList = similarSeriesList,
                            loadError = "",
                            isLoading = false,
                            pageNumber = it.pageNumber+1
                        )
                    }
                }
                is Resource.Error -> {
                    state.update {
                        it.copy(
                            loadError = result.message!!,
                            isLoading = false
                        )
                    }
                }
                else -> {}
            }
        }
    }
}