package com.hamza.wallpap.ui.screens.common

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Wallpaper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hamza.wallpap.ui.screens.wallpaper.WallpaperFullScreenViewModel
import com.hamza.wallpap.ui.theme.*
import com.hamza.wallpap.util.setWallPaper

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun SetWallpaperDialog(
    dialogState: MutableState<Boolean>,
    context: Context,
    wallpaperFullScreenViewModel: WallpaperFullScreenViewModel,
    fullUrl: String
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
            fullUrl
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
    fullUrl: String
) {
    Card(
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 0.dp,
    ) {
        Column(
            modifier
                .background(color = MaterialTheme.colors.background)
        ) {
            Icon(
                tint = MaterialTheme.colors.iconColor,
                imageVector = Icons.Outlined.Wallpaper,
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Set Wallpaper as",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.textColor
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
                        setWallPaper(context, fullUrl, wallpaperFullScreenViewModel.setWallpaperAs)
                        dialogState.value = false
                        wallpaperFullScreenViewModel.interstitalState.value = true
                    }) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "System",
                        color = MaterialTheme.colors.bottomAppBarContentColor,
                        modifier = Modifier,
                        style = MaterialTheme.typography.subtitle1,
                        fontFamily = maven_pro_regular
                    )
                }

                TextButton(
                    modifier = Modifier.padding(5.dp),
                    onClick = {
                        wallpaperFullScreenViewModel.setWallpaperAs = 2
                        setWallPaper(
                            context,
                            fullUrl,
                            wallpaperFullScreenViewModel.setWallpaperAs
                        )
                        dialogState.value = false
                        wallpaperFullScreenViewModel.interstitalState.value = true
                    }) {
                    Text(
                        text = "Lock Screen",
                        color = MaterialTheme.colors.bottomAppBarContentColor,
                        modifier = Modifier,
                        style = MaterialTheme.typography.subtitle1,
                        fontFamily = maven_pro_regular
                    )
                }

                TextButton(
                    modifier = Modifier.padding(5.dp),
                    onClick = {
                        wallpaperFullScreenViewModel.setWallpaperAs = 3
                        setWallPaper(
                            context,
                            fullUrl,
                            wallpaperFullScreenViewModel.setWallpaperAs
                        )
                        dialogState.value = false
                        wallpaperFullScreenViewModel.interstitalState.value = true
                    }) {
                    Text(
                        text = "Both",
                        color = MaterialTheme.colors.bottomAppBarContentColor,
                        modifier = Modifier,
                        style = MaterialTheme.typography.subtitle1,
                        fontFamily = maven_pro_regular
                    )
                }
            }
        }
    }
}