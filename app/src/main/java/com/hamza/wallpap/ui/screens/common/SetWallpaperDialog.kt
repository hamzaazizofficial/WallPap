package com.hamza.wallpap.ui.screens.common

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Wallpaper
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 0.dp,
    ) {
        var state by remember { mutableStateOf(true) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .background(color = MaterialTheme.colors.background)
        ) {
//            Icon(
//                tint = MaterialTheme.colors.iconColor,
//                imageVector = Icons.Outlined.Wallpaper,
//                contentDescription = null,
//                modifier = Modifier
//                    .padding(top = 35.dp)
//                    .height(70.dp)
//                    .fillMaxWidth(),
//            )

            Image(
                painterResource(id = R.drawable.wattpad),
                contentDescription = null,
                contentScale = ContentScale.Fit, modifier = modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .size(80.dp)
            )



            Column(modifier = Modifier.padding(16.dp)) {

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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .selectableGroup()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Center Crop",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.subtitle1,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colors.textColor
                        )
                        RadioButton(
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colors.bottomAppBarContentColor,
                                unselectedColor = MaterialTheme.colors.topAppBarTitle
                            ),
                            selected = state,
                            onClick = { state = true },
                            modifier = Modifier.semantics {
                                contentDescription = "Localized Description"
                            }
                        )
                    }

                    Spacer(modifier = modifier.padding(horizontal = 4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Fit",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.subtitle1,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colors.textColor
                        )
                        RadioButton(
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colors.bottomAppBarContentColor,
                                unselectedColor = MaterialTheme.colors.topAppBarTitle
                            ),
                            selected = !state,
                            onClick = { state = false },
                            modifier = Modifier.semantics {
                                contentDescription = "Localized Description"
                            }
                        )
                    }

                }

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
                        setWallPaper(
                            context,
                            fullUrl,
                            wallpaperFullScreenViewModel.setWallpaperAs,
                            finalImageBitmap
                        )
                        dialogState.value = false
                        wallpaperFullScreenViewModel.interstitialState.value = true
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
                        setWallPaper(
                            context,
                            fullUrl,
                            wallpaperFullScreenViewModel.setWallpaperAs,
                            finalImageBitmap
                        )
                        dialogState.value = false
                        wallpaperFullScreenViewModel.interstitialState.value = true
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
                        setWallPaper(
                            context,
                            fullUrl,
                            wallpaperFullScreenViewModel.setWallpaperAs,
                            finalImageBitmap
                        )
                        dialogState.value = false
                        wallpaperFullScreenViewModel.interstitialState.value = true
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