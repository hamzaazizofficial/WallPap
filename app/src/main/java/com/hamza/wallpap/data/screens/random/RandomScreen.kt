package com.hamza.wallpap.data.screens.random

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.hamza.wallpap.data.screens.common.RandomListContent
import com.hamza.wallpap.ui.theme.maven_pro_regular
import com.hamza.wallpap.ui.theme.textColor
import com.hamza.wallpap.util.isOnline
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun RandomScreen(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    randomScreenViewModel: RandomScreenViewModel
) {
    val context = LocalContext.current
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
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            RandomListContent(items = items, navController, randomScreenViewModel)
        }
    }
}