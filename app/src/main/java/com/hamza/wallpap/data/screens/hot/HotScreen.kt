package com.hamza.wallpap.data.screens.hot

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.hamza.wallpap.data.screens.common.HotListContent
import kotlinx.coroutines.launch

@Composable
fun HotScreen(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    hotViewModel: HotViewModel
) {
    val scope = rememberCoroutineScope()
    val items = hotViewModel.itemsFlow.collectAsLazyPagingItems()
    BackHandler {
        if (scaffoldState.drawerState.isOpen) {
            scope.launch {
                scaffoldState.drawerState.close()
            }
        } else {
            navController.navigate("home_screen")
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.background(Color.Black)
    ) {
        HotListContent(items = items, navController, hotViewModel)
    }
}