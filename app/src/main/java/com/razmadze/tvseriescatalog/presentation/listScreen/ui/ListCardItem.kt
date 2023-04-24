package com.razmadze.tvseriescatalog.presentation.listScreen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.razmadze.tvseriescatalog.models.SeriesEntry
import com.razmadze.tvseriescatalog.presentation.theme.CardTextColor
import com.razmadze.tvseriescatalog.presentation.theme.VoteIconColor

@Composable
fun ListCardItem(
    entry: SeriesEntry,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(130.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { navController.navigate("series_detail_screen/${entry.id}") }
            ),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
    ) {
        Row(
            Modifier
                .padding(15.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imagePath)
                    .crossfade(true)
                    .build(),
                contentDescription = entry.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(CircleShape),
                placeholder = painterResource(id = R.drawable.loading_icon),
                error = painterResource(id = R.drawable.image_not_available_narrow)
            )
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxHeight()
                    .weight(0.8f)
            ) {
                Text(
                    text = entry.name,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    color = CardTextColor
                )
                Spacer(modifier = Modifier.size(10.dp))
                Row (horizontalArrangement = Arrangement.spacedBy(20.dp)){
                    Text(
                        text = entry.firstAirDate ?: "",
                        fontSize = 13.sp
                    )
                    Row {
                        Icon(
                            imageVector = Icons.Default.StarRate,
                            tint = VoteIconColor,
                            contentDescription = null,
                            modifier = Modifier
                                .size(18.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Text(
                            text =  stringResource(id=R.string.vote_text, entry.voteAverage.voteAverageFormatter()),
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}