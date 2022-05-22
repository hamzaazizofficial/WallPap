package com.hamza.wallpap.data.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.hamza.wallpap.data.navigation.Screen
import com.hamza.wallpap.data.screens.common.ListContent
import com.hamza.wallpap.data.screens.search.SearchViewModel

@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val getAllImages = homeViewModel.getAllImages.collectAsLazyPagingItems()
//    val searchViewModel: SearchViewModel = hiltViewModel()
//    val searchedImages = searchViewModel.searchedImages.collectAsLazyPagingItems()


    var items by remember { mutableStateOf(getAllImages)}

    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClicked = {
                    navController.navigate(Screen.Search.route)
                }
            )
        },
        content = {
            Column(verticalArrangement = Arrangement.Center) {
//                Text(text = "Anime", fontSize = 26.sp, modifier = Modifier.background(Color.Green)
//                    .clickable {
//                        searchViewModel.searchHeroes("anime")
//                        items = searchedImages
//                    })
//                Spacer(modifier = Modifier.padding(6.dp))
                ListContent(items = items, navController)
            }
        }
    )
}