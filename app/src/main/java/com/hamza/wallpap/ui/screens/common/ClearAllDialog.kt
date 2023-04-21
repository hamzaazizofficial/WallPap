package com.hamza.wallpap.ui.screens.common

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hamza.wallpap.R
import com.hamza.wallpap.data.local.dao.FavUrlsViewModel
import com.hamza.wallpap.navigation.Screen
import com.hamza.wallpap.ui.screens.editor.CustomWallpaperViewModel
import com.hamza.wallpap.ui.theme.bottomAppBarBackgroundColor
import com.hamza.wallpap.ui.theme.maven_pro_regular
import com.hamza.wallpap.ui.theme.textColor

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ClearAllDialog(
    dialogState: MutableState<Boolean>,
    context: Context,
    customWallpaperViewModel: CustomWallpaperViewModel,
    dialogText: String,
    currentRoute: String?,
    favUrlsViewModel: FavUrlsViewModel,
) {
    Dialog(
        onDismissRequest = { dialogState.value = false },
        properties = DialogProperties(usePlatformDefaultWidth = true),
    ) {
        ClearAllDialogUI(
            modifier = Modifier,
            dialogState,
            context,
            customWallpaperViewModel,
            dialogText,
            currentRoute,
            favUrlsViewModel
        )
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ClearAllDialogUI(
    modifier: Modifier = Modifier,
    dialogState: MutableState<Boolean>,
    context: Context,
    customWallpaperViewModel: CustomWallpaperViewModel,
    dialogText: String,
    currentRoute: String?,
    favUrlsViewModel: FavUrlsViewModel,
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 2.dp,
    ) {
        Column(
            modifier
                .background(color = MaterialTheme.colors.background)
        ) {

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(id = R.drawable.wattpad),
                        contentDescription = null,
                        contentScale = ContentScale.Fit, modifier = Modifier
                            .padding(top = 0.dp, bottom = 10.dp)
                            .size(90.dp)
                    )

                    Divider(
                        Modifier.padding(10.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colors.textColor
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {

                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colors.textColor,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = maven_pro_regular
                                    )
                                ) {
                                    append(dialogText)
                                }
                            })
                    }
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.bottomAppBarBackgroundColor
                    ), horizontalArrangement = Arrangement.SpaceAround
            ) {

                TextButton(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .width(80.dp),
                    onClick = {
                        if (currentRoute.equals(Screen.CustomWallpaperEditorScreen.route)) {
                            customWallpaperViewModel.contentScale.value = ContentScale.Crop
                            customWallpaperViewModel.bgImageFullUrl.value = null
                            customWallpaperViewModel.bgImageRegularUrl.value = null
                            customWallpaperViewModel.bgImageUri.value = null
                            customWallpaperViewModel.bgBoxColor.value = Color(0xF1FFFFFF)
                            customWallpaperViewModel.wallpaperTextColor.value = Color(0xF1FFFFFF)
                            customWallpaperViewModel.wallpaperText.value = ""
                            customWallpaperViewModel.shareWallpaperVisible.value = false
                            customWallpaperViewModel.selectedTextColorIndex.value = 0
                            customWallpaperViewModel.selectedBgColorIndex.value = 20
                            customWallpaperViewModel.redColorSliderPosition.value = 0f
                            customWallpaperViewModel.greenColorSliderPosition.value = 0f
                            customWallpaperViewModel.blueColorSliderPosition.value = 0f
                            customWallpaperViewModel.alphaColorSliderPosition.value = 255f
                            customWallpaperViewModel.redColorValue.value = 0
                            customWallpaperViewModel.greenColorValue.value = 0
                            customWallpaperViewModel.blueColorValue.value = 0
                            customWallpaperViewModel.alphaColorValue.value = 255
                            customWallpaperViewModel.bgImageTransparency.value = 1f
                            customWallpaperViewModel.imageTransparencySliderPosition.value = 1f
                            dialogState.value = false
                        }
                        if (currentRoute.equals(Screen.Favourite.route)){
                            favUrlsViewModel.deleteAllFavouriteUrls()
                            Toast.makeText(context, "Removed all images!", Toast.LENGTH_SHORT).show()
                            dialogState.value = false
                        }
                    }) {
                    Text(
                        text = "Yes",
                        color = MaterialTheme.colors.textColor,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.subtitle1.fontSize,
                            fontWeight = FontWeight.Bold,
                            fontFamily = maven_pro_regular
                        )
                    )
                }

                TextButton(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .width(80.dp),
                    onClick = {
                        dialogState.value = false
                    }) {
                    Text(
                        text = "No",
                        color = MaterialTheme.colors.textColor,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.subtitle1.fontSize,
                            fontWeight = FontWeight.Bold,
                            fontFamily = maven_pro_regular
                        )
                    )
                }
            }
        }
    }
}