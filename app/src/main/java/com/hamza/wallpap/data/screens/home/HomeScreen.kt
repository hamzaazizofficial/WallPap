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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hamza.wallpap.data.screens.common.HomeListContent
import com.hamza.wallpap.data.screens.common.SearchChips
import com.hamza.wallpap.data.screens.search.SearchChipsViewModel
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
    val searchChipsViewModel: SearchChipsViewModel = viewModel()

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
            searchChipsViewModel.wallpaperItems.forEachIndexed { index, s ->
                SearchChips(
                    text = s.title,
                    selected = searchChipsViewModel.selectedIndex.value == index,
                    onClick = {
                        searchChipsViewModel.selectedIndex.value = index
                        homeViewModel.query.value = searchChipsViewModel.wallpaperItems[index].query
                    }
                )
                Spacer(modifier = Modifier.padding(horizontal = 6.dp))
            }
        }
        HomeListContent(items = homeViewModel.itemsFlow.collectAsLazyPagingItems(), navController, homeViewModel)
    }
}