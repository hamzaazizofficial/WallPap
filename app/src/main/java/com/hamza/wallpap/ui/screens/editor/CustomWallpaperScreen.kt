package com.hamza.wallpap.ui.screens.editor

import android.content.Context
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FormatColorFill
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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

    var modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val captureController = rememberCaptureController()
    val scope = rememberCoroutineScope()

    var xPosText by remember { mutableStateOf(0) }
    var yPosText by remember { mutableStateOf(0) }

    var bgImageBlurSliderPosition by remember { mutableStateOf(0f) }
    var bgImageBlurValue by remember(bgImageBlurSliderPosition) { mutableStateOf(0f) }

//    var matrix by remember { mutableStateOf(ColorMatrix()) }
//    val colorFilter = ColorFilter.colorMatrix(matrix)

    var scale by remember { mutableStateOf(1f) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            customWallpaperViewModel.selectedImageUri.value = uri
            customWallpaperViewModel.bgImageFullUrl.value = null
        }
    )

    BackHandler {
        navController.popBackStack()
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
        sheetState = modalBottomSheetState,
        sheetContent = {
            EditorBottomSheet(
                navController,
                customWallpaperViewModel.colorItems,
                customWallpaperViewModel,
                modalBottomSheetState,
                scope,
                randomItems,
                context,
                singlePhotoPickerLauncher
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
                                visible = customWallpaperViewModel.bgImageFullUrl.value != null
                                        || customWallpaperViewModel.bgBoxColor.value != Color(
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
                                .pointerInput(Unit) {
                                    detectTransformGestures { centroid, pan, zoom, rotation ->
                                        scale *= zoom
                                    }
                                }
                                .pointerInput(Unit) {
                                    detectDragGestures { change, dragAmount ->
                                        change.consume()
                                        xPosText += dragAmount.x.toInt()
                                        yPosText += dragAmount.y.toInt()
                                    }
                                }
                                .background(customWallpaperViewModel.bgBoxColor.value)
                        ) {
                            if (
                                customWallpaperViewModel.bgImageFullUrl.value == null
                                && customWallpaperViewModel.selectedImageUri.value == null
                                && customWallpaperViewModel.bgBoxColor.value == Color(0xF1FFFFFF)
                                && customWallpaperViewModel.wallpaperText.value == ""
                            ) {
                                Text(
                                    text = "Add something here.",
                                    fontSize = 18.sp,
                                    fontFamily = maven_pro_regular
                                )
                            }

                            when {
                                customWallpaperViewModel.bgImageFullUrl.value != null -> {
                                    SubcomposeAsyncImage(
                                        model = customWallpaperViewModel.bgImageFullUrl.value,
                                        contentScale = ContentScale.Crop,
                                        contentDescription = null,
                                        alpha = customWallpaperViewModel.bgImageTransparency.value
                                    ) {
                                        val state = painter.state
                                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                            SubcomposeAsyncImage(
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Crop,
                                                model = customWallpaperViewModel.bgImageRegularUrl.value,
                                                contentDescription = null,
                                                alpha = customWallpaperViewModel.bgImageTransparency.value
                                            )
                                        } else {
                                            SubcomposeAsyncImageContent(modifier = Modifier.fillMaxSize())
                                        }
                                    }
                                }
                                customWallpaperViewModel.selectedImageUri.value != null -> {
                                    AsyncImage(
                                        model = customWallpaperViewModel.selectedImageUri.value,
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop,
                                        alpha = customWallpaperViewModel.bgImageTransparency.value
                                    )
                                }
                            }

                            Text(
                                text = customWallpaperViewModel.wallpaperText.value,
                                style = TextStyle(
                                    fontStyle = customWallpaperViewModel.wallpaperTextFontStyle.value,
                                    color = customWallpaperViewModel.wallpaperTextColor.value,
                                    fontSize = customWallpaperViewModel.wallpaperTextSize.value * scale,
                                    textDecoration = customWallpaperViewModel.wallpaperTextDecoration.value,
                                    fontWeight = customWallpaperViewModel.wallpaperTextFontWeight.value,
                                    textAlign = customWallpaperViewModel.wallpaperTextAlign.value,
                                    fontFamily = customWallpaperViewModel.textFontFamily.value
                                ),
                                modifier = Modifier
                                    .padding(12.dp)
                                    .offset { IntOffset(xPosText, yPosText) }
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
//
//                                        }
                                    )
                            )

                        }
                    }
                }
            }
        }
    )
}



