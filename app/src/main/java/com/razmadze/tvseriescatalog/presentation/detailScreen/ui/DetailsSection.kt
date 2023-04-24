package com.razmadze.tvseriescatalog.presentation.detailScreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.razmadze.tvseriescatalog.R
import com.razmadze.tvseriescatalog.extensions.voteAverageFormatter
import com.razmadze.tvseriescatalog.extensions.voteCountFormatter
import com.razmadze.tvseriescatalog.models.SeriesDetails
import com.razmadze.tvseriescatalog.presentation.detailScreen.ui.similarList.DetailsSimilarSeriesList
import com.razmadze.tvseriescatalog.presentation.theme.BackButtonBackgroundColor
import com.razmadze.tvseriescatalog.presentation.theme.BackButtonColor
import com.razmadze.tvseriescatalog.presentation.theme.BackgroundColor
import com.razmadze.tvseriescatalog.presentation.theme.VoteIconColor
import com.razmadze.tvseriescatalog.utils.Constants

@Composable
fun DetailsSection(
    seriesInfo: SeriesDetails,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        item {
            Box(modifier = Modifier.height(220.dp)) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Constants.IMAGE_BASE_URL + seriesInfo.backdropPath)
                        .crossfade(true)
                        .build(),
                    contentDescription = seriesInfo.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(220.dp)
                        .fillMaxWidth(),
                    placeholder = painterResource(id = R.drawable.loading_icon),
                    error = painterResource(id = R.drawable.image_not_available)
                )
                BackButton(
                    navController = navController,
                    modifier = Modifier
                        .size(30.dp)
                        .offset(16.dp, 16.dp)
                        .align(Alignment.TopStart)
                        .background(BackButtonBackgroundColor, CircleShape)
                )
            }
        }
        item {
            Column(
                modifier = modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = seriesInfo.name,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                Row {
                    Column(modifier = Modifier.padding(end = 30.dp)) {
                        Row {
                            Text(
                                text = stringResource(id = R.string.seasons_text),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(end = 2.dp)
                            )
                            Text(
                                text = seriesInfo.numberOfSeasons.toString(),
                                fontSize = 13.sp,
                                modifier = Modifier.padding(end = 2.dp)
                            )
                        }
                        Row {
                            Text(
                                text = stringResource(id = R.string.first_air_date),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = seriesInfo.firstAirDate,
                                fontSize = 13.sp
                            )
                        }
                    }
                    Row {
                        Icon(
                            imageVector = Icons.Default.StarRate,
                            tint = VoteIconColor,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .size(25.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Column {
                            Text(
                                text = stringResource(id=R.string.vote_text, seriesInfo.voteAverage.voteAverageFormatter()),
                                fontSize = 17.sp
                            )
                            Text(
                                text = stringResource(id = R.string.vote_category, seriesInfo.voteCount.voteCountFormatter()),
                                fontSize = 10.sp
                            )
                        }
                    }
                }
                Text(
                    text = seriesInfo.overview,
                    fontSize = 13.sp
                )
            }
        }
        item {
            DetailsSimilarSeriesList(
                navController = navController,
                seriesId = seriesInfo.id
            )
        }
    }
}

@Composable
fun BackButton(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = BackButtonColor,
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .align(Alignment.Center)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}