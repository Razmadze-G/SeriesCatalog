package com.razmadze.tvseriescatalog.presentation.detailScreen.ui

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.razmadze.tvseriescatalog.models.SeriesDetails
import com.razmadze.tvseriescatalog.utils.Resource

@Composable
fun DetailsStateWrapper (
    seriesInfo: Resource<SeriesDetails>,
    navController: NavController,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when (seriesInfo) {
        is Resource.Success -> {
            DetailsSection(
                seriesInfo = seriesInfo.data!!,
                navController = navController,
                modifier = modifier
            )
        }
        is Resource.Error -> {
            Text(
                text = seriesInfo.message!!,
                color = Color.Black,
                modifier = modifier
            )
        }
        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )
        }
    }
}