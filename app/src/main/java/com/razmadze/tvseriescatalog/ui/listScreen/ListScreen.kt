package com.razmadze.tvseriescatalog.ui.listScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.razmadze.tvseriescatalog.MainActivity
import com.razmadze.tvseriescatalog.R
import com.razmadze.tvseriescatalog.ui.theme.BackgroundColor
import com.razmadze.tvseriescatalog.ui.theme.TVSeriesCatalogTheme

@Composable
fun ListScreen(
    navController: NavHostController
) {
    Surface(
        color = BackgroundColor,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.tv_series_catalog_logo),
                contentDescription = stringResource(id = R.string.tv_series_catalog_logo_description),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 10.dp)
                    .size(220.dp, 50.dp)
            )
//            Row {
//            }
            SeriesList(navController = navController)
        }
    }
}

@Composable
fun SeriesList(
    navController: NavController,
    viewModel: ListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = 12.dp,
            vertical = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val itemCount = state.value.seriesList.size
        items(itemCount) {
            if (it >= itemCount - 1 && state.value.pageNumber <= 500 && !state.value.isLoading) {
                viewModel.loadPagination()
            }
            ListCardItem(
                entry = state.value.seriesList[it],
                navController = navController
            )
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.value.isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary
            )
        }
        if (state.value.loadError.isNotEmpty()) {
            RetrySection(error = state.value.loadError) {
                viewModel.loadPagination()
            }
        }
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(
            error,
            color = Color.Blue,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TVSeriesCatalogTheme {
        MainActivity()
    }
}