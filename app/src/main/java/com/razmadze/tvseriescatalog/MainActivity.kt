package com.razmadze.tvseriescatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.razmadze.tvseriescatalog.presentation.detailScreen.ui.DetailsScreen
import com.razmadze.tvseriescatalog.presentation.listScreen.ui.ListScreen
import com.razmadze.tvseriescatalog.presentation.theme.TVSeriesCatalogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TVSeriesCatalogTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "list_screen"
                ) {
                    composable("list_screen") {
                        ListScreen(navController)
                    }
                    composable(
                        "series_detail_screen/{seriesId}",
                        arguments = listOf(
                            navArgument("seriesId") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        val seriesId = remember {
                            it.arguments?.getInt("seriesId")
                        }
                        if (seriesId != null) {
                            DetailsScreen(
                                seriesId = seriesId,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}