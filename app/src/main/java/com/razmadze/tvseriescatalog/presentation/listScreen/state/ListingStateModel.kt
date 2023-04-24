package com.razmadze.tvseriescatalog.presentation.listScreen.state

import com.razmadze.tvseriescatalog.models.SeriesEntry

data class ListingStateModel(
    val seriesList: List<SeriesEntry> = emptyList(),
    val loadError: String = "",
    val isLoading: Boolean = true,
    var pageNumber: Int = 1,
    val endReached: Boolean = false,
)