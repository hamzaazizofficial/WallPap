package com.hamza.wallpap.data.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.hamza.wallpap.data.navigation.Screen
import com.hamza.wallpap.data.screens.common.ListContent
import com.hamza.wallpap.data.screens.common.SearchChips
import com.hamza.wallpap.data.screens.search.SearchViewModel

@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val searchViewModel: SearchViewModel = hiltViewModel()
    val items = homeViewModel.itemsFlow.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClicked = {
                    navController.navigate(Screen.Search.route)
                }
            )
        },
        bottomBar = {
            BottomBar()
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.background(Color.Black)
        ) {

            Row(
                modifier = Modifier
                    .padding(horizontal = 7.dp, vertical = 7.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                searchViewModel.wallpaperItems.forEachIndexed { index, s ->
                    SearchChips(
                        text = s.title,
                        selected = searchViewModel.selectedIndex.value == index,
                        onClick = {
                            searchViewModel.selectedIndex.value = index
                            homeViewModel.query.value = searchViewModel.wallpaperItems[index].query
                        }
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                }
            }
            ListContent(items = items, navController)
        }
    }
}