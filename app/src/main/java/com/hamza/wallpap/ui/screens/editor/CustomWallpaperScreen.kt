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
import androidx.compose.ui.draw.rotate
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
import kotlinx.coroutines.delay
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

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val captureController = rememberCaptureController()
    val scope = rememberCoroutineScope()

    var xPosText by remember { mutableStateOf(0) }
    var yPosText by remember { mutableStateOf(0) }

    val matrix by remember { mutableStateOf(ColorMatrix()) }
    matrix.setToSaturation(customWallpaperViewModel.saturationSliderValue.value)
    val colorFilter = ColorFilter.colorMatrix(matrix)

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            customWallpaperViewModel.bgImageUri.value = uri
            customWallpaperViewModel.bgImageFullUrl.value = null
            customWallpaperViewModel.imageRotate.value = 0f
            customWallpaperViewModel.contentScale.value = ContentScale.Crop
        }
    )

    BackHandler {
        navController.popBackStack()
        customWallpaperViewModel.editorDropDownExpanded.value = false
    }

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxHeight(),
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
                                        || customWallpaperViewModel.bgImageUri.value != null
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
                                        if (customWallpaperViewModel.editorDropDownExpanded.value) {
                                            customWallpaperViewModel.editorDropDownExpanded.value =
                                                false
                                            scope.launch {
                                                delay(2000)
                                                captureController.capture()
                                            }

                                        } else {
                                            captureController.capture()
                                        }
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
                                    tint = MaterialTheme.colors.topAppBarTitle
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
                                    tint = MaterialTheme.colors.topAppBarTitle
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
                                    tint = MaterialTheme.colors.topAppBarTitle
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
//                            saveMediaToStorage(bitmap.asAndroidBitmap(), context)
                            customWallpaperViewModel.savedImageBitmap.value =
                                bitmap.asAndroidBitmap()
                            customWallpaperViewModel.saveImageBottomSheet.value = true
                            scope.launch {
                                modalBottomSheetState.show()
                            }
                        }
                    }) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .pointerInput(Unit) {
                                    detectTransformGestures { centroid, pan, zoom, rotation ->
//                                        scale *= zoom
                                        customWallpaperViewModel.wallpaperTextSize.value *= zoom
                                        customWallpaperViewModel.textSliderPosition.value *= zoom
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
                                && customWallpaperViewModel.bgImageUri.value == null
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
                                        contentScale = customWallpaperViewModel.contentScale.value,
                                        contentDescription = null,
                                        alpha = customWallpaperViewModel.bgImageTransparency.value,
                                        colorFilter = colorFilter
                                    ) {
                                        val state = painter.state
                                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                            SubcomposeAsyncImage(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .rotate(customWallpaperViewModel.imageRotate.value),
                                                contentScale = contentScale,
                                                model = customWallpaperViewModel.bgImageRegularUrl.value,
                                                contentDescription = null,
                                                alpha = customWallpaperViewModel.bgImageTransparency.value,
                                                colorFilter = colorFilter
                                            )
                                            if (customWallpaperViewModel.editorDropDownExpanded.value) {
                                                Surface(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(80.dp)
//                                                        .alpha(ContentAlpha.high)
                                                        .align(Alignment.TopCenter)
                                                        .animateContentSize(),
                                                    color = MaterialTheme.colors.topAppBarBackgroundColor
                                                ) {
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth(),
                                                        verticalArrangement = Arrangement.Center,
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .padding(
                                                                    start = 8.dp,
                                                                    end = 0.dp,
                                                                    bottom = 4.dp,
                                                                    top = 4.dp
                                                                ),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.SpaceEvenly
                                                        ) {
                                                            androidx.compose.material3.Slider(
                                                                modifier = Modifier.weight(4.5f),
                                                                value = customWallpaperViewModel.saturationSliderPosition.value,
                                                                onValueChange = {
                                                                    customWallpaperViewModel.saturationSliderPosition.value =
                                                                        it
                                                                },
                                                                valueRange = 0f..10f,
                                                                onValueChangeFinished = {
                                                                    customWallpaperViewModel.saturationSliderValue.value =
                                                                        customWallpaperViewModel.saturationSliderPosition.value
                                                                },
                                                                colors = androidx.compose.material3.SliderDefaults.colors(
                                                                    activeTrackColor = MaterialTheme.colors.bottomAppBarContentColor.copy(
                                                                        0.5f
                                                                    ),
                                                                    thumbColor = MaterialTheme.colors.bottomAppBarContentColor
                                                                )
                                                            )

                                                            IconButton(onClick = {
                                                                customWallpaperViewModel.saturationSliderPosition.value =
                                                                    1f
                                                                customWallpaperViewModel.saturationSliderValue.value =
                                                                    1f
                                                            }, modifier = Modifier.weight(1f)) {
                                                                Icon(
                                                                    imageVector = if (customWallpaperViewModel.saturationSliderPosition.value ==
                                                                        1f &&
                                                                        customWallpaperViewModel.saturationSliderValue.value ==
                                                                        1f
                                                                    ) Icons.Default.InvertColors else Icons.Default.InvertColorsOff,
                                                                    contentDescription = null,
                                                                    tint = MaterialTheme.colors.iconColor
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            SubcomposeAsyncImageContent(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .rotate(customWallpaperViewModel.imageRotate.value)
                                            )
                                            if (customWallpaperViewModel.editorDropDownExpanded.value) {
                                                Surface(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(80.dp)
//                                                        .alpha(ContentAlpha.high)
                                                        .align(Alignment.TopCenter)
                                                        .animateContentSize(),
                                                    color = MaterialTheme.colors.topAppBarBackgroundColor
                                                ) {
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth(),
                                                        verticalArrangement = Arrangement.Center,
                                                        horizontalAlignment = Alignment.CenterHorizontally
                                                    ) {
                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .padding(
                                                                    start = 8.dp,
                                                                    end = 0.dp,
                                                                    bottom = 4.dp,
                                                                    top = 4.dp
                                                                ),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            horizontalArrangement = Arrangement.SpaceEvenly
                                                        ) {
                                                            androidx.compose.material3.Slider(
                                                                modifier = Modifier.weight(4.5f),
                                                                value = customWallpaperViewModel.saturationSliderPosition.value,
                                                                onValueChange = {
                                                                    customWallpaperViewModel.saturationSliderPosition.value =
                                                                        it
                                                                },
                                                                valueRange = 0f..10f,
                                                                onValueChangeFinished = {
                                                                    customWallpaperViewModel.saturationSliderValue.value =
                                                                        customWallpaperViewModel.saturationSliderPosition.value
                                                                },
                                                                colors = androidx.compose.material3.SliderDefaults.colors(
                                                                    activeTrackColor = MaterialTheme.colors.bottomAppBarContentColor.copy(
                                                                        0.5f
                                                                    ),
                                                                    thumbColor = MaterialTheme.colors.bottomAppBarContentColor
                                                                )
                                                            )

                                                            IconButton(onClick = {
                                                                customWallpaperViewModel.saturationSliderPosition.value =
                                                                    1f
                                                                customWallpaperViewModel.saturationSliderValue.value =
                                                                    1f
                                                            }, modifier = Modifier.weight(1f)) {
                                                                Icon(
                                                                    imageVector = if (customWallpaperViewModel.saturationSliderPosition.value ==
                                                                        1f &&
                                                                        customWallpaperViewModel.saturationSliderValue.value ==
                                                                        1f
                                                                    ) Icons.Default.InvertColors else Icons.Default.InvertColorsOff,
                                                                    contentDescription = null,
                                                                    tint = MaterialTheme.colors.iconColor
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                customWallpaperViewModel.bgImageUri.value != null -> {
                                    AsyncImage(
                                        model = customWallpaperViewModel.bgImageUri.value,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .rotate(customWallpaperViewModel.imageRotate.value),
                                        contentScale = customWallpaperViewModel.contentScale.value,
                                        alpha = customWallpaperViewModel.bgImageTransparency.value,
                                        colorFilter = colorFilter
                                    )
                                    if (customWallpaperViewModel.editorDropDownExpanded.value) {
                                        Surface(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(80.dp)
//                                                .alpha(ContentAlpha.high)
                                                .align(Alignment.TopCenter)
                                                .animateContentSize(),
                                            color = MaterialTheme.colors.topAppBarBackgroundColor
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(
                                                            start = 8.dp,
                                                            end = 0.dp,
                                                            bottom = 4.dp,
                                                            top = 4.dp
                                                        ),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceEvenly
                                                ) {
                                                    androidx.compose.material3.Slider(
                                                        modifier = Modifier.weight(4.5f),
                                                        value = customWallpaperViewModel.saturationSliderPosition.value,
                                                        onValueChange = {
                                                            customWallpaperViewModel.saturationSliderPosition.value =
                                                                it
                                                        },
                                                        valueRange = 0f..10f,
                                                        onValueChangeFinished = {
                                                            customWallpaperViewModel.saturationSliderValue.value =
                                                                customWallpaperViewModel.saturationSliderPosition.value
                                                        },
                                                        colors = androidx.compose.material3.SliderDefaults.colors(
                                                            activeTrackColor = MaterialTheme.colors.bottomAppBarContentColor.copy(
                                                                0.5f
                                                            ),
                                                            thumbColor = MaterialTheme.colors.bottomAppBarContentColor
                                                        )
                                                    )

                                                    IconButton(onClick = {
                                                        customWallpaperViewModel.saturationSliderPosition.value =
                                                            1f
                                                        customWallpaperViewModel.saturationSliderValue.value =
                                                            1f
                                                    }, modifier = Modifier.weight(1f)) {
                                                        Icon(
                                                            imageVector = if (customWallpaperViewModel.saturationSliderPosition.value ==
                                                                1f &&
                                                                customWallpaperViewModel.saturationSliderValue.value ==
                                                                1f
                                                            ) Icons.Default.InvertColors else Icons.Default.InvertColorsOff,
                                                            contentDescription = null,
                                                            tint = MaterialTheme.colors.iconColor
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
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



