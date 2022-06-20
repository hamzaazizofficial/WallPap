package com.hamza.wallpap.data.screens.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.hamza.wallpap.data.screens.common.ListContent
import com.hamza.wallpap.data.screens.common.SearchChips
import com.hamza.wallpap.data.screens.search.SearchViewModel
import kotlinx.coroutines.launch

@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    scaffoldState: ScaffoldState
) {
    val searchViewModel: SearchViewModel = hiltViewModel()
    val items = homeViewModel.itemsFlow.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()
    val activity = (LocalContext.current as? Activity)

    BackHandler {
        if (scaffoldState.drawerState.isOpen) {
            scope.launch {
                scaffoldState.drawerState.close()
            }
        } else {
            activity?.finish()
        }
    }

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
        ListContent(items = items, navController, homeViewModel)
    }
}