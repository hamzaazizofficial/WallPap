package com.hamza.wallpap.data.screens.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hamza.wallpap.data.screens.common.HomeListContent
import com.hamza.wallpap.data.screens.firestore.amoled.AmoledScreen
import com.hamza.wallpap.data.screens.search.SearchChips
import com.hamza.wallpap.ui.theme.systemBarColor
import kotlinx.coroutines.launch

@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    scaffoldState: ScaffoldState
) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.systemBarColor)

    val scope = rememberCoroutineScope()
    val activity = (LocalContext.current as? Activity)
    val items = homeViewModel.itemsFlow.collectAsLazyPagingItems()

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
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {

        Row(
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 7.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            homeViewModel.wallpaperItems.forEachIndexed { index, s ->
                SearchChips(
                    text = s.title,
                    selected = homeViewModel.selectedIndex.value == index,
                    onClick = {
                        homeViewModel.selectedIndex.value = index
                        homeViewModel.query.value = homeViewModel.wallpaperItems[index].query
                    }
                )
                Spacer(modifier = Modifier.padding(horizontal = 6.dp))
            }
        }
        HomeListContent(items = items, navController, homeViewModel)
//        AmoledScreen(navController)
    }
}