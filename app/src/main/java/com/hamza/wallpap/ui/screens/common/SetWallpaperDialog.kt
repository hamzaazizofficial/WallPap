package com.hamza.wallpap.ui.screens.common

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hamza.wallpap.R
import com.hamza.wallpap.ui.screens.wallpaper.WallpaperFullScreenViewModel
import com.hamza.wallpap.ui.theme.*
import com.hamza.wallpap.util.setWallPaper
import kotlinx.coroutines.*

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun SetWallpaperDialog(
    dialogState: MutableState<Boolean>,
    context: Context,
    wallpaperFullScreenViewModel: WallpaperFullScreenViewModel,
    fullUrl: String,
    finalImageBitmap: Bitmap?,
) {
    Dialog(
        onDismissRequest = { dialogState.value = false },
        properties = DialogProperties(usePlatformDefaultWidth = true),
    ) {
        SetWallpaperDialogUI(
            modifier = Modifier,
            dialogState,
            context,
            wallpaperFullScreenViewModel,
            fullUrl,
            finalImageBitmap
        )
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun SetWallpaperDialogUI(
    modifier: Modifier = Modifier,
    dialogState: MutableState<Boolean>,
    context: Context,
    wallpaperFullScreenViewModel: WallpaperFullScreenViewModel,
    fullUrl: String,
    finalImageBitmap: Bitmap?,
) {
    BoxWithConstraints {
        constraints
        when {
            maxHeight <= 370.dp -> {
                /* Small Card will show for phones in landscape orientation */
                Card(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(8.dp, 4.dp, 8.dp, 8.dp),
                    elevation = 0.dp,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier
                            .background(color = MaterialTheme.colors.background)
                    ) {
                        Image(
                            painterResource(id = R.drawable.wattpad),
                            contentDescription = null,
                            contentScale = ContentScale.Fit, modifier = modifier
                                .padding(top = 8.dp, bottom = 6.dp)
                                .size(50.dp)
                        )

                        Column(modifier = modifier) {

                            Text(
                                text = "Choose Scale",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .fillMaxWidth(),
                                style = MaterialTheme.typography.subtitle1,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colors.textColor,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = modifier.padding(vertical = 2.dp))

                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                                    .background(
                                        color = MaterialTheme.colors.customDialogBottomColor
                                    ),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {

                                TextButton(
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = if (wallpaperFullScreenViewModel.scaleFitState) MaterialTheme.colors.background else MaterialTheme.colors.customDialogBottomColor,
                                        contentColor = if (wallpaperFullScreenViewModel.scaleFitState) MaterialTheme.colors.bottomAppBarContentColor else MaterialTheme.colors.topAppBarTitle,
                                    ),
                                    modifier = Modifier.padding(4.dp),
                                    onClick = {
                                        wallpaperFullScreenViewModel.scaleFitState = true
                                        wallpaperFullScreenViewModel.scaleCropState = false
                                        wallpaperFullScreenViewModel.scaleStretchState = false
                                        wallpaperFullScreenViewModel.finalScaleState =
                                            wallpaperFullScreenViewModel.scaleFitState
                                    }) {
                                    Text(
                                        textAlign = TextAlign.Center,
                                        text = "Fit",
                                        modifier = Modifier,
                                        style = MaterialTheme.typography.subtitle1,
                                        fontFamily = maven_pro_regular,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                TextButton(
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = if (wallpaperFullScreenViewModel.scaleCropState) MaterialTheme.colors.background else MaterialTheme.colors.customDialogBottomColor,
                                        contentColor = if (wallpaperFullScreenViewModel.scaleCropState) MaterialTheme.colors.bottomAppBarContentColor else MaterialTheme.colors.topAppBarTitle,
                                    ),
                                    modifier = Modifier.padding(4.dp),
                                    onClick = {
                                        wallpaperFullScreenViewModel.scaleFitState = false
                                        wallpaperFullScreenViewModel.scaleCropState = true
                                        wallpaperFullScreenViewModel.scaleStretchState = false
                                        wallpaperFullScreenViewModel.finalScaleState =
                                            wallpaperFullScreenViewModel.scaleCropState
                                    }) {
                                    Text(
                                        text = "Center Crop",
                                        modifier = Modifier,
                                        style = MaterialTheme.typography.subtitle1,
                                        fontFamily = maven_pro_regular,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                TextButton(
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = if (wallpaperFullScreenViewModel.scaleStretchState) MaterialTheme.colors.background else MaterialTheme.colors.customDialogBottomColor,
                                        contentColor = if (wallpaperFullScreenViewModel.scaleStretchState) MaterialTheme.colors.bottomAppBarContentColor else MaterialTheme.colors.topAppBarTitle,
                                    ),
                                    modifier = Modifier.padding(4.dp),
                                    onClick = {
                                        wallpaperFullScreenViewModel.scaleFitState = false
                                        wallpaperFullScreenViewModel.scaleCropState = false
                                        wallpaperFullScreenViewModel.scaleStretchState = true
                                        wallpaperFullScreenViewModel.finalScaleState =
                                            wallpaperFullScreenViewModel.scaleStretchState
                                    }) {
                                    Text(
                                        text = "Stretch",
                                        modifier = Modifier,
                                        style = MaterialTheme.typography.subtitle1,
                                        fontFamily = maven_pro_regular,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = modifier.padding(vertical = 8.dp))

                            Text(
                                text = stringResource(id = R.string.set_wallpaper),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .fillMaxWidth(),
                                style = MaterialTheme.typography.subtitle1,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colors.textColor,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = modifier.padding(vertical = 2.dp))

                        }
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .background(
                                    color = MaterialTheme.colors.customDialogBottomColor
                                ),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {

                            TextButton(
                                modifier = Modifier.padding(4.dp),
                                onClick = {
                                    wallpaperFullScreenViewModel.setWallpaperAs = 1
                                    setWallpaperWithToast(
                                        context,
                                        fullUrl,
                                        finalImageBitmap,
                                        wallpaperFullScreenViewModel
                                    )
                                    dialogState.value = false
                                }) {
                                Text(
                                    textAlign = TextAlign.Center,
                                    text = stringResource(id = R.string.system),
                                    color = MaterialTheme.colors.bottomAppBarContentColor,
                                    modifier = Modifier,
                                    style = MaterialTheme.typography.subtitle1,
                                    fontFamily = maven_pro_regular,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            TextButton(
                                modifier = Modifier.padding(4.dp),
                                onClick = {
                                    wallpaperFullScreenViewModel.setWallpaperAs = 2
                                    setWallpaperWithToast(
                                        context,
                                        fullUrl,
                                        finalImageBitmap,
                                        wallpaperFullScreenViewModel
                                    )
                                    dialogState.value = false
                                }) {
                                Text(
                                    text = stringResource(id = R.string.lock),
                                    color = MaterialTheme.colors.bottomAppBarContentColor,
                                    modifier = Modifier,
                                    style = MaterialTheme.typography.subtitle1,
                                    fontFamily = maven_pro_regular,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            TextButton(
                                modifier = Modifier.padding(4.dp),
                                onClick = {
                                    wallpaperFullScreenViewModel.setWallpaperAs = 3
                                    setWallpaperWithToast(
                                        context,
                                        fullUrl,
                                        finalImageBitmap,
                                        wallpaperFullScreenViewModel
                                    )
                                    dialogState.value = false
                                }) {
                                Text(
                                    text = stringResource(id = R.string.both),
                                    color = MaterialTheme.colors.bottomAppBarContentColor,
                                    modifier = Modifier,
                                    style = MaterialTheme.typography.subtitle1,
                                    fontFamily = maven_pro_regular,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
            maxHeight > 370.dp -> {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
                    elevation = 0.dp,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier
                            .background(color = MaterialTheme.colors.background)
                    ) {
                        Image(
                            painterResource(id = R.drawable.wattpad),
                            contentDescription = null,
                            contentScale = ContentScale.Fit, modifier = modifier
                                .padding(top = 10.dp, bottom = 10.dp)
                                .size(80.dp)
                        )

                        Column(modifier = modifier) {

                            Text(
                                text = "Choose Scale",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(top = 5.dp)
                                    .fillMaxWidth(),
                                style = MaterialTheme.typography.subtitle1,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colors.textColor,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = modifier.padding(vertical = 4.dp))

                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                                    .background(
                                        color = MaterialTheme.colors.customDialogBottomColor
                                    ),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {

                                TextButton(
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = if (wallpaperFullScreenViewModel.scaleFitState) MaterialTheme.colors.background else MaterialTheme.colors.customDialogBottomColor,
                                        contentColor = if (wallpaperFullScreenViewModel.scaleFitState) MaterialTheme.colors.bottomAppBarContentColor else MaterialTheme.colors.topAppBarTitle,
                                    ),
                                    modifier = Modifier.padding(5.dp),
                                    onClick = {
                                        wallpaperFullScreenViewModel.scaleFitState = true
                                        wallpaperFullScreenViewModel.scaleCropState = false
                                        wallpaperFullScreenViewModel.scaleStretchState = false
                                        wallpaperFullScreenViewModel.finalScaleState =
                                            wallpaperFullScreenViewModel.scaleFitState
                                    }) {
                                    Text(
                                        textAlign = TextAlign.Center,
                                        text = "Fit",
                                        modifier = Modifier,
                                        style = MaterialTheme.typography.subtitle1,
                                        fontFamily = maven_pro_regular,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                TextButton(
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = if (wallpaperFullScreenViewModel.scaleCropState) MaterialTheme.colors.background else MaterialTheme.colors.customDialogBottomColor,
                                        contentColor = if (wallpaperFullScreenViewModel.scaleCropState) MaterialTheme.colors.bottomAppBarContentColor else MaterialTheme.colors.topAppBarTitle,
                                    ),
                                    modifier = Modifier.padding(5.dp),
                                    onClick = {
                                        wallpaperFullScreenViewModel.scaleFitState = false
                                        wallpaperFullScreenViewModel.scaleCropState = true
                                        wallpaperFullScreenViewModel.scaleStretchState = false
                                        wallpaperFullScreenViewModel.finalScaleState =
                                            wallpaperFullScreenViewModel.scaleCropState
                                    }) {
                                    Text(
                                        text = "Center Crop",
                                        modifier = Modifier,
                                        style = MaterialTheme.typography.subtitle1,
                                        fontFamily = maven_pro_regular,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                TextButton(
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = if (wallpaperFullScreenViewModel.scaleStretchState) MaterialTheme.colors.background else MaterialTheme.colors.customDialogBottomColor,
                                        contentColor = if (wallpaperFullScreenViewModel.scaleStretchState) MaterialTheme.colors.bottomAppBarContentColor else MaterialTheme.colors.topAppBarTitle,
                                    ),
                                    modifier = Modifier.padding(5.dp),
                                    onClick = {
                                        wallpaperFullScreenViewModel.scaleFitState = false
                                        wallpaperFullScreenViewModel.scaleCropState = false
                                        wallpaperFullScreenViewModel.scaleStretchState = true
                                        wallpaperFullScreenViewModel.finalScaleState =
                                            wallpaperFullScreenViewModel.scaleStretchState
                                    }) {
                                    Text(
                                        text = "Stretch",
                                        modifier = Modifier,
                                        style = MaterialTheme.typography.subtitle1,
                                        fontFamily = maven_pro_regular,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = modifier.padding(vertical = 10.dp))

                            Text(
                                text = stringResource(id = R.string.set_wallpaper),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(top = 5.dp)
                                    .fillMaxWidth(),
                                style = MaterialTheme.typography.subtitle1,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colors.textColor,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = modifier.padding(vertical = 4.dp))

                        }
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                                .background(
                                    color = MaterialTheme.colors.customDialogBottomColor
                                ),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {

                            TextButton(
                                modifier = Modifier.padding(5.dp),
                                onClick = {
                                    wallpaperFullScreenViewModel.setWallpaperAs = 1
                                    setWallpaperWithToast(
                                        context,
                                        fullUrl,
                                        finalImageBitmap,
                                        wallpaperFullScreenViewModel
                                    )
                                    dialogState.value = false
                                }) {
                                Text(
                                    textAlign = TextAlign.Center,
                                    text = stringResource(id = R.string.system),
                                    color = MaterialTheme.colors.bottomAppBarContentColor,
                                    modifier = Modifier,
                                    style = MaterialTheme.typography.subtitle1,
                                    fontFamily = maven_pro_regular,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            TextButton(
                                modifier = Modifier.padding(5.dp),
                                onClick = {
                                    wallpaperFullScreenViewModel.setWallpaperAs = 2
                                    setWallpaperWithToast(
                                        context,
                                        fullUrl,
                                        finalImageBitmap,
                                        wallpaperFullScreenViewModel
                                    )
                                    dialogState.value = false
                                }) {
                                Text(
                                    text = stringResource(id = R.string.lock),
                                    color = MaterialTheme.colors.bottomAppBarContentColor,
                                    modifier = Modifier,
                                    style = MaterialTheme.typography.subtitle1,
                                    fontFamily = maven_pro_regular,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            TextButton(
                                modifier = Modifier.padding(5.dp),
                                onClick = {
                                    wallpaperFullScreenViewModel.setWallpaperAs = 3
                                    setWallpaperWithToast(
                                        context,
                                        fullUrl,
                                        finalImageBitmap,
                                        wallpaperFullScreenViewModel
                                    )
                                    dialogState.value = false
                                }) {
                                Text(
                                    text = stringResource(id = R.string.both),
                                    color = MaterialTheme.colors.bottomAppBarContentColor,
                                    modifier = Modifier,
                                    style = MaterialTheme.typography.subtitle1,
                                    fontFamily = maven_pro_regular,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
fun setWallpaperWithToast(
    context: Context,
    fullUrl: String,
    finalImageBitmap: Bitmap?,
    wallpaperFullScreenViewModel: WallpaperFullScreenViewModel,
) {
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        showToast(context, "Error setting wallpaper: ${exception.localizedMessage}")
    }
    val wallpaperCoroutineScope = CoroutineScope(Dispatchers.Main)

    wallpaperCoroutineScope.launch(coroutineExceptionHandler) {
        try {
            setWallPaper(
                context,
                fullUrl,
                wallpaperFullScreenViewModel.setWallpaperAs,
                finalImageBitmap,
                wallpaperFullScreenViewModel
            )
            showToast(context, "Setting wallpaper...")
            delay(1000)
        } catch (e: Exception) {
            showToast(context, "Error setting wallpaper: ${e.localizedMessage}")
        }
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}