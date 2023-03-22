package com.hamza.wallpap.ui.screens.editor

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FormatColorFill
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hamza.wallpap.R
import com.hamza.wallpap.model.CustomWallpaperBackgroundColor
import com.hamza.wallpap.model.UnsplashImage
import com.hamza.wallpap.ui.theme.*
import com.hamza.wallpap.util.saveMediaToStorage
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(
    ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class, ExperimentalAnimationApi::class
)
@Composable
fun CustomWallpaperScreen(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    customWallpaperViewModel: CustomWallpaperViewModel,
    randomItems: LazyPagingItems<UnsplashImage>,
    context: Context,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.systemBarColor)
    val itemList = mutableListOf<CustomWallpaperBackgroundColor>()
    itemList.add(CustomWallpaperBackgroundColor(Color(0xFFFFFFFF), 8))
    itemList.add(CustomWallpaperBackgroundColor(Color(0xFF000000), 8))
    itemList.add(CustomWallpaperBackgroundColor(Color(0xFF41FDF7), 8))
    itemList.add(CustomWallpaperBackgroundColor(Color(0xFFFF5722), 8))
    itemList.add(CustomWallpaperBackgroundColor(Color(0xFFE91E63), 8))
    itemList.add(CustomWallpaperBackgroundColor(Color(0xFF8BC34A), 8))
    itemList.add(CustomWallpaperBackgroundColor(Color(0xFF3F51B5), 8))
    itemList.add(CustomWallpaperBackgroundColor(Color(0xFF9C27B0), 8))

    var modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val captureController = rememberCaptureController()
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
                        containerColor = MaterialTheme.colors.bottomAppBarBackgroundColor,
                        contentColor = MaterialTheme.colors.editorBottomAppBarContentColor,
                        floatingActionButton = {
                            AnimatedVisibility(
                                visible = customWallpaperViewModel.bgImageUrl.value != null
                                        || customWallpaperViewModel.boxColor.value != Color(
                                    0xF1FFFFFF
                                )
                                        || customWallpaperViewModel.wallpaperText.value != "",
                                enter = scaleIn() + fadeIn(),
                                exit = scaleOut() + fadeOut()
                            )
                            {
                                androidx.compose.material3.SmallFloatingActionButton(
                                    containerColor = MaterialTheme.colors.bottomAppBarContentColor,
                                    contentColor = Color.White,
                                    shape = RoundedCornerShape(4.dp),
                                    onClick = {
                                        captureController.capture()
                                        customWallpaperViewModel.shareWallpaperVisible.value = true
                                    })
                                {
                                    Icon(
                                        Icons.Default.Download,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
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
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.editorBottomAppBarContentColor
                                )
                            }

                            androidx.compose.material3.IconButton(
                                onClick = {
                                    customWallpaperViewModel.textBottomSheet.value = true
                                    scope.launch {
                                        modalBottomSheetState.show()
                                    }
                                }) {
                                Icon(
                                    Icons.Default.TextFields,
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.editorBottomAppBarContentColor
                                )
                            }

                            androidx.compose.material3.IconButton(
                                onClick = {
                                    customWallpaperViewModel.bgImageBottomSheet.value = true
                                    scope.launch {
                                        modalBottomSheetState.show()
                                    }
                                }) {
                                Icon(
                                    Icons.Default.Image,
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.editorBottomAppBarContentColor
                                )
                            }
                        }
                    )
                }) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(MaterialTheme.colors.background),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Capturable(controller = captureController, onCaptured = { bitmap, error ->
                        if (bitmap != null) {
                            saveMediaToStorage(bitmap.asAndroidBitmap(), context)
                            customWallpaperViewModel.savedImageBitmap.value =
                                bitmap.asAndroidBitmap()
                        }
                    }) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(customWallpaperViewModel.boxColor.value)
                        ) {
                            if (
                                customWallpaperViewModel.bgImageUrl.value == null
                                && customWallpaperViewModel.boxColor.value == Color(0xF1FFFFFF)
                                && customWallpaperViewModel.wallpaperText.value == ""
                            ) {
                                Text(
                                    text = "Add something here.",
                                    fontSize = 22.sp,
                                    fontFamily = maven_pro_regular
                                )
                            }
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
                                    fontFamily = customWallpaperViewModel.textFontFamily.value
                                ),
                                modifier = Modifier
                                    .padding(12.dp)
                                    .combinedClickable(
                                        onClick = {
                                            if (customWallpaperViewModel.wallpaperText.value.isNotEmpty()) {
                                                customWallpaperViewModel.textBottomSheet.value =
                                                    true
                                                scope.launch {
                                                    modalBottomSheetState.show()
                                                }
                                            }
                                        },
//                                        onLongClick = {
//                                            customWallpaperViewModel.wallpaperText.value = ""
//                                        }
                                    )
                            )
                        }
                    }
                }
            }
        })
}



