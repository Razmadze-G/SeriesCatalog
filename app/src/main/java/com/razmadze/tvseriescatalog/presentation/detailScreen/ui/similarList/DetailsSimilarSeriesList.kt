package com.razmadze.tvseriescatalog.presentation.detailScreen.ui.similarList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.razmadze.tvseriescatalog.presentation.detailScreen.vm.DetailsViewModel
import com.razmadze.tvseriescatalog.presentation.theme.BackgroundColor

@Composable
fun DetailsSimilarSeriesList(
    seriesId: Int,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

    LazyRow(
        modifier = modifier
            .height(250.dp)
            .fillMaxWidth()
            .background(BackgroundColor)
            .padding(bottom = 30.dp),
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        val itemCount = state.value.similarSeriesList.size

        items(itemCount) {
            if (it >= itemCount - 1 && state.value.pageNumber <= 500 && !state.value.isLoading) {
                viewModel.loadPagination(seriesId)
            }
            DetailsSimilarCardItem(
                entry = state.value.similarSeriesList[it],
                navController = navController
            )
        }
    }
}