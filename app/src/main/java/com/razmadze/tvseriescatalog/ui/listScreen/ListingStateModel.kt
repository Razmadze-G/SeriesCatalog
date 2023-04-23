package com.razmadze.tvseriescatalog.ui.listScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.razmadze.tvseriescatalog.models.SeriesEntry

data class ListingStateModel(
    val seriesList: List<SeriesEntry> = emptyList(),
    val loadError: String = "",
    val isLoading: Boolean = true,
    var pageNumber: Int = 1
)