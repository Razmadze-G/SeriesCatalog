package com.razmadze.tvseriescatalog.presentation.detailScreen.state

import com.razmadze.tvseriescatalog.models.SeriesEntry

data class DetailsStateModel(
    val similarSeriesList: List<SeriesEntry> = emptyList(),
    val loadError: String = "",
    val isLoading: Boolean = true,
    var pageNumber: Int = 1
)