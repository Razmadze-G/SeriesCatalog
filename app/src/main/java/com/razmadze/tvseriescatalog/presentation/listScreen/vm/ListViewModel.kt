package com.razmadze.tvseriescatalog.presentation.listScreen.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razmadze.tvseriescatalog.models.SeriesEntry
import com.razmadze.tvseriescatalog.presentation.listScreen.state.ListingStateModel
import com.razmadze.tvseriescatalog.repository.SeriesRepository
import com.razmadze.tvseriescatalog.utils.Constants.IMAGE_BASE_URL
import com.razmadze.tvseriescatalog.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repository: SeriesRepository) : ViewModel() {
    private val seriesList = mutableStateOf<List<SeriesEntry>>(listOf())
    private val seriesListForSearch = mutableStateOf<List<SeriesEntry>>(listOf())
    val state = MutableStateFlow(ListingStateModel())

    init {
        loadPagination()
    }

    fun loadPagination() {
        viewModelScope.launch {
            state.update {
                it.copy(isLoading = true)
            }
            when (val result = repository.getPopularSeriesList(state.value.pageNumber)) {
                is Resource.Success -> {
                    val entries = result.data!!.seriesList.mapIndexed { _ , value ->
                        val firstAirYear = if(value.firstAirDate.isNotEmpty()){
                            value.firstAirDate.substring(0,4)
                        } else value.firstAirDate
                        SeriesEntry(
                            imagePath = IMAGE_BASE_URL + value.posterPath,
                            name = value.name,
                            id = value.id,
                            overview = value.overview,
                            voteAverage = value.voteAverage,
                            firstAirDate = firstAirYear
                        )
                    }
                    seriesList.value += entries
                    state.update {
                        it.copy(
                            seriesList = seriesList.value,
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

    fun loadPaginationWithSearch(searchKeyword: String) {
        viewModelScope.launch {
            state.update {
                it.copy(
                    isLoading = true
                )
            }
            when (val result = repository.getSearchSeriesList(state.value.pageNumber, searchKeyword)) {
                //TODO API not working correctly, additional filter for names should be added!
                is Resource.Success -> {
                    val entries = result.data!!.seriesList.mapIndexed { _ , value ->
                        val firstAirYear = if(value.firstAirDate.isNotEmpty()){
                            value.firstAirDate.substring(0,4)
                        } else value.firstAirDate
                        SeriesEntry(
                            imagePath = IMAGE_BASE_URL + value.posterPath,
                            name = value.name,
                            id = value.id,
                            overview = value.overview,
                            voteAverage = value.voteAverage,
                            firstAirDate = firstAirYear
                        )
                    }
                    seriesListForSearch.value += entries

                    state.update {
                        it.copy(
                            seriesList = seriesListForSearch.value,
                            loadError = "",
                            isLoading = false,
                            pageNumber = it.pageNumber+1,
                            endReached = entries.size < 20
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

    fun clearSeriesList() {
        state.update {
            it.copy(
                seriesList = emptyList(),
                pageNumber = 1,
                isLoading = false
            )
        }
        seriesListForSearch.value = emptyList()
    }
}