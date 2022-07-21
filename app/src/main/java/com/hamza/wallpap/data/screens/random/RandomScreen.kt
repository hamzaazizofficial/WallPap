package com.hamza.wallpap.data.screens.random

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.hamza.wallpap.data.screens.common.RandomListContent
import kotlinx.coroutines.launch

@Composable
fun RandomScreen(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    randomScreenViewModel: RandomScreenViewModel
) {
    val scope = rememberCoroutineScope()
    val items = randomScreenViewModel.itemsFlow.collectAsLazyPagingItems()
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
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        RandomListContent(items = items, navController, randomScreenViewModel)
    }
}