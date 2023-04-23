package com.razmadze.tvseriescatalog.ui.detailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.razmadze.tvseriescatalog.models.Series
import com.razmadze.tvseriescatalog.ui.BackButton
import com.razmadze.tvseriescatalog.utils.Resource

@Composable
fun DetailsScreen(
    seriesId: Int,
    navController: NavController,
    viewModel:DetailsViewModel = hiltViewModel()
){
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val seriesInfo = produceState<Resource<Series>>(
            initialValue = Resource.Loading()
        ){
            value = viewModel.getSeries(seriesId)
        }.value

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ){

            BackButton(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(16.dp, 16.dp)
                    .fillMaxHeight(0.2f)
                    .align(Alignment.TopCenter)
            )

            DetailsStateWrapper(
                seriesInfo = seriesInfo,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 80.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
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
}