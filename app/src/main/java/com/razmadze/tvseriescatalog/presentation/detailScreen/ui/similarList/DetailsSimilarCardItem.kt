package com.razmadze.tvseriescatalog.presentation.detailScreen.ui.similarList

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.razmadze.tvseriescatalog.R
import com.razmadze.tvseriescatalog.models.SeriesEntry
import com.razmadze.tvseriescatalog.presentation.theme.BackgroundColor
import com.razmadze.tvseriescatalog.presentation.theme.BorderColor
import com.razmadze.tvseriescatalog.utils.Constants

@Composable
fun DetailsSimilarCardItem(
    entry: SeriesEntry,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(0.dp, 4.dp)
            .fillMaxSize()
            .border(1.dp, BorderColor, RoundedCornerShape(4.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    navController.navigate("series_detail_screen/${entry.id}")
                }
            ),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
    )
    {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Constants.IMAGE_BASE_URL + entry.imagePath)
                .crossfade(true)
                .build(),
            contentDescription = entry.name,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(240.dp)
                .width(135.dp)
                .background(BackgroundColor),
            placeholder = painterResource(id = R.drawable.loading_icon),
            error = painterResource(id = R.drawable.image_not_available_narrow)
        )
    }
}