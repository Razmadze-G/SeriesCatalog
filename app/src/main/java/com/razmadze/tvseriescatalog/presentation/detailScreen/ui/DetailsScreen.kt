package com.razmadze.tvseriescatalog.presentation.detailScreen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.razmadze.tvseriescatalog.models.SeriesDetails
import com.razmadze.tvseriescatalog.presentation.detailScreen.vm.DetailsViewModel
import com.razmadze.tvseriescatalog.utils.Resource

@Composable
fun DetailsScreen(
    seriesId: Int,
    navController: NavController,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val seriesInfo = produceState<Resource<SeriesDetails>>(
        initialValue = Resource.Loading()
    ) {
        value = viewModel.getSeriesDetails(seriesId)
    }.value

    LaunchedEffect(Unit) {
        viewModel.loadPagination(seriesId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        DetailsStateWrapper(
            seriesInfo = seriesInfo,
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(RoundedCornerShape(15.dp))
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(
                    top = 50.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        )
    }
}