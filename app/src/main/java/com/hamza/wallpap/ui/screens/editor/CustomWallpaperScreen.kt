package com.hamza.wallpap.ui.screens.editor

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.outlined.FormatColorFill
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberImagePainter
import com.hamza.wallpap.R
import com.hamza.wallpap.model.CustomWallpaperBackgroundColor
import com.hamza.wallpap.model.UnsplashImage
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CustomWallpaperScreen(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    customWallpaperViewModel: CustomWallpaperViewModel,
    randomItems: LazyPagingItems<UnsplashImage>,
    context: Context,
) {
    val itemList = mutableListOf<CustomWallpaperBackgroundColor>()
    itemList.add(CustomWallpaperBackgroundColor(Color(0xFFFFFFFF), 8))
    itemList.add(CustomWallpaperBackgroundColor(Color(0xFF485049), 8))
    itemList.add(CustomWallpaperBackgroundColor(Color(0xFF41FDF7), 8))
    itemList.add(CustomWallpaperBackgroundColor(Color(0xFFFF5722), 8))
    itemList.add(CustomWallpaperBackgroundColor(Color(0xFFE91E63), 8))
    itemList.add(CustomWallpaperBackgroundColor(Color(0xFF8BC34A), 8))
    itemList.add(CustomWallpaperBackgroundColor(Color(0xFF3F51B5), 8))
    itemList.add(CustomWallpaperBackgroundColor(Color(0xFF9C27B0), 8))

    var modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
        sheetState = modalBottomSheetState,
        sheetContent = {
            EditorBottomSheet(
                navController,
                itemList,
                customWallpaperViewModel,
                modalBottomSheetState,
                scope,
                randomItems,
                context
            )
        },
        content = {
            androidx.compose.material3.Scaffold(
                bottomBar = {
                    androidx.compose.material3.BottomAppBar(
                        floatingActionButton = {
                            androidx.compose.material3.SmallFloatingActionButton(
                                shape = RoundedCornerShape(4.dp),
                                onClick = {}) {
                                Icon(Icons.Default.Add, contentDescription = null)
                            }
                        },
                        actions = {
                            androidx.compose.material3.IconButton(
                                onClick = {
                                    customWallpaperViewModel.bgColorBottomSheet.value = true
                                    scope.launch {
                                        modalBottomSheetState.show()
                                    }
                                }) {
                                Icon(
                                    Icons.Outlined.FormatColorFill,
                                    contentDescription = null
                                )
                            }

                            androidx.compose.material3.IconButton(
                                onClick = {
                                    customWallpaperViewModel.textBottomSheet.value = true
                                    scope.launch {
                                        modalBottomSheetState.show()
                                    }
                                }) {
                                Icon(Icons.Default.TextFields, contentDescription = null)
                            }

                            androidx.compose.material3.IconButton(
                                onClick = {
                                    customWallpaperViewModel.bgImageBottomSheet.value = true
                                    scope.launch {
                                        modalBottomSheetState.show()
                                    }
                                }) {
                                Icon(Icons.Default.Image, contentDescription = null)
                            }
                        }
                    )
                }) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(Color.White),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.padding(vertical = 22.dp))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(300.dp)
                            .height(600.dp)
                            .border(border = BorderStroke(1.dp, Color.Black))
                            .background(customWallpaperViewModel.boxColor.value)
                    ) {
                        if (customWallpaperViewModel.bgImageUrl.value != null) {
                            val painter =
                                rememberImagePainter(data = customWallpaperViewModel.bgImageUrl.value) {
                                    crossfade(durationMillis = 1000)
                                    error(R.drawable.ic_placeholder)
                                }
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                painter = painter,
                                contentDescription = "Unsplash Image",
                                contentScale = ContentScale.Crop,
                                alpha = customWallpaperViewModel.bgImageTransparency.value
                            )
                        }

                        Text(
                            text = customWallpaperViewModel.wallpaperText.value,
                            style = TextStyle(
                                fontStyle = customWallpaperViewModel.wallpaperTextFontStyle.value,
                                color = customWallpaperViewModel.wallpaperTextColor.value,
                                fontSize = customWallpaperViewModel.wallpaperTextSize.value,
                                textDecoration = customWallpaperViewModel.wallpaperTextDecoration.value,
                                fontWeight = customWallpaperViewModel.wallpaperTextFontWeight.value,
                                textAlign = customWallpaperViewModel.wallpaperTextAlign.value,
                            ),
                            modifier = Modifier
                                .padding(12.dp)
                                .clickable {
                                    if (customWallpaperViewModel.wallpaperText.value.isNotEmpty()) {
                                        customWallpaperViewModel.textBottomSheet.value = true
                                        scope.launch {
                                            modalBottomSheetState.show()
                                        }
                                    }
                                }
                        )
                    }
                }
            }
        })
}


