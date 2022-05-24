package com.hamza.wallpap.data.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.hamza.wallpap.data.navigation.Screen
import com.hamza.wallpap.data.screens.common.ListContent
import com.hamza.wallpap.data.screens.common.SearchChips
import com.hamza.wallpap.data.screens.common.SearchChipsViewModel
import com.hamza.wallpap.data.screens.search.SearchViewModel

@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val getAllImages = homeViewModel.getAllImages.collectAsLazyPagingItems()
//    val searchChipsViewModel: SearchChipsViewModel = viewModel()
    val searchViewModel: SearchViewModel = hiltViewModel()
    val searchedImages = searchViewModel.searchedImages.collectAsLazyPagingItems()
//    val wallpaperItems = searchChipsViewModel.wallpaperItems

    var items by remember { mutableStateOf(getAllImages) }

    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClicked = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.background(Color.Black)
        ) {
//                Text(text = "Anime", fontSize = 26.sp, modifier = Modifier.background(Color.Green)
//                    .clickable {
//                        searchViewModel.searchHeroes("anime")
//                        items = searchedImages
//                    })
//                Spacer(modifier = Modifier.padding(6.dp))

//            var selectedIndex by remember {
//                mutableStateOf(0)
//            }

//            if (searchChipsViewModel.selectedIndex.value == 0) {
//                items = getAllImages
//            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 7.dp, vertical = 7.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                searchViewModel.wallpaperItems.forEachIndexed { index, s ->
                    SearchChips(
                        text = s,
                        selected = searchViewModel.selectedIndex.value == index,
                        onClick = {
                            searchViewModel.selectedIndex.value = index
                            searchViewModel.searchHeroes(searchViewModel.wallpaperItems[index])
                            items = searchedImages
                        }
                    )

                    Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                }
            }

            ListContent(items = items, navController)
        }
    }
}