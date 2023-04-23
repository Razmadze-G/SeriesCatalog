package com.razmadze.tvseriescatalog.ui.detailScreen

import androidx.compose.foundation.layout.offset
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.razmadze.tvseriescatalog.models.Series
import com.razmadze.tvseriescatalog.utils.Resource

@Composable
fun DetailsStateWrapper (
    seriesInfo: Resource<Series>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when (seriesInfo) {
        is Resource.Success -> {
            DetailsSection(
                seriesInfo = seriesInfo.data!!,
                modifier = modifier
                    .offset(y = (-20).dp)
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