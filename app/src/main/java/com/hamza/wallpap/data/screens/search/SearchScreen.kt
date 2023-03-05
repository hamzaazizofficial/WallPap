package com.hamza.wallpap.data.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.hamza.wallpap.data.screens.common.HomeListContent
import com.hamza.wallpap.data.screens.home.HomeViewModel
import com.hamza.wallpap.ui.theme.maven_pro_regular
import com.hamza.wallpap.ui.theme.textColor
import com.hamza.wallpap.util.isOnline

@ExperimentalPagingApi
@ExperimentalCoilApi
@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    homeViewModel: HomeViewModel
) {
    val searchQuery by searchViewModel.searchQuery
    val searchedImages = searchViewModel.searchedImages.collectAsLazyPagingItems()
    val context = LocalContext.current
//    val listState: LazyListState = rememberLazyListState()

    Scaffold(
        topBar = {
            SearchWidget(
                text = searchQuery,
                onTextChange = {
                    searchViewModel.updateSearchQuery(query = it)
                    searchViewModel.searchHeroes(query = it)
                },
                onSearchClicked = {
                    searchViewModel.searchHeroes(query = it)
                },
                onCloseClicked = {
                    navController.popBackStack()
                },
                onNavBackPop = {
                    navController.popBackStack()
                }
            )
        },
        content = {
            if (!isOnline(context)) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {
                    Icon(
                        tint = Color.White,
                        imageVector = Icons.Default.NetworkCheck, contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )

                    Spacer(modifier = Modifier.padding(10.dp))

                    Text(
                        text = "Check your Network Connection.",
                        color = MaterialTheme.colors.textColor,
                        fontFamily = maven_pro_regular,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                }
            } else {
                HomeListContent(items = searchedImages, navController, homeViewModel)
            }
        }
    )
}