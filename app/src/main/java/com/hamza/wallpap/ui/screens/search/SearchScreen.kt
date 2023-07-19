package com.hamza.wallpap.ui.screens.search

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.hamza.wallpap.R
import com.hamza.wallpap.ui.screens.common.HomeListContent
import com.hamza.wallpap.ui.screens.home.HomeViewModel
import com.hamza.wallpap.ui.theme.maven_pro_regular
import com.hamza.wallpap.ui.theme.textColor
import com.hamza.wallpap.ui.theme.topAppBarTitle
import com.hamza.wallpap.util.isOnline

@RequiresApi(Build.VERSION_CODES.M)
@ExperimentalPagingApi
@ExperimentalCoilApi
@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    homeViewModel: HomeViewModel,
    //    refreshState: SwipeRefreshState,
    context: Context,
) {
    val searchQuery by searchViewModel.searchQuery
    val searchedImages = searchViewModel.searchedImages.collectAsLazyPagingItems()

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
                    navController.navigate("home_screen")
                },
                onNavBackPop = {
                    navController.popBackStack()
                }
            )
        },
        content = { padding ->
            if (!isOnline(context)) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                        .padding(padding)
                ) {
                    Icon(
                        tint = MaterialTheme.colors.topAppBarTitle,
                        imageVector = Icons.Default.NetworkCheck, contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    Text(
                        text = stringResource(id = R.string.check_network) + "\n" + stringResource(
                            id = R.string.reopen_app
                        ),
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
                HomeListContent(
                    items = searchedImages,
                    navController,
                    homeViewModel,
//                    { items.refresh() },
//                    refreshState
                )
            }
        }
    )
}