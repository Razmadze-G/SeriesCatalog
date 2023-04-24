package com.razmadze.tvseriescatalog.presentation.listScreen.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.razmadze.tvseriescatalog.R
import com.razmadze.tvseriescatalog.presentation.listScreen.vm.ListViewModel
import com.razmadze.tvseriescatalog.presentation.theme.BackButtonColor
import com.razmadze.tvseriescatalog.presentation.theme.BackgroundColor
import com.razmadze.tvseriescatalog.presentation.theme.HeaderColor
import com.razmadze.tvseriescatalog.presentation.theme.luckiestGuy

@Composable
fun ListScreen(
    navController: NavHostController
) {
    val searchOpen = remember { mutableStateOf(false) }

    Surface(
        color = BackgroundColor,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.header_text),
                    fontFamily = luckiestGuy,
                    color = HeaderColor,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(0.dp, 10.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = BackButtonColor,
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .clickable {
                            searchOpen.value = !searchOpen.value
                        }
                )
            }
            SeriesList(navController = navController, searchOpen.value)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SeriesList(
    navController: NavController,
    openSearch: Boolean,
    viewModel: ListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val text = remember { mutableStateOf("") }

    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = 12.dp
        ),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        val itemCount = state.value.seriesList.size
        if (openSearch) {
            stickyHeader {
                TextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(onSearch = {
                        if (text.value.length > 2) {
                            viewModel.clearSeriesList()
                            viewModel.loadPaginationWithSearch(text.value)
                        }
                    }),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (text.value.isNotEmpty()) {
                                    text.value = ""
                                    viewModel.clearSeriesList()
                                    viewModel.loadPagination()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = stringResource(id = R.string.close_icon_description),
                                tint = Color.White
                            )
                        }
                    },
                    placeholder = { Text(text = stringResource(id = R.string.text_field_placeholder)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BackgroundColor)
                        .padding(start = 8.dp, end = 8.dp, bottom = 15.dp),
                )
            }
        }
        if (state.value.seriesList.isNotEmpty()) {
            items(itemCount) {
                if (it >= itemCount - 1 && state.value.pageNumber <= 500 && !state.value.isLoading && !state.value.endReached) {
                    if (text.value.length > 2)
                        viewModel.loadPaginationWithSearch(text.value)
                    else
                        viewModel.loadPagination()
                }

                ListCardItem(
                    entry = state.value.seriesList[it],
                    navController = navController
                )
            }
        } else {
            if (!state.value.isLoading) {
                item {
                    Text(
                        text = stringResource(id = R.string.result_not_found),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 150.dp)
                    )
                }
            }
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.value.isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.onError
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
            Text(text = stringResource(id = R.string.retry))
        }
    }
}