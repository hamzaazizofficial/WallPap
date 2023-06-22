package com.hamza.wallpap.ui.screens.editor

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.hamza.wallpap.R
import com.hamza.wallpap.model.CustomWallpaperBackgroundColor
import com.hamza.wallpap.model.UnsplashImage
import com.hamza.wallpap.ui.screens.common.ColorPickerDialog
import com.hamza.wallpap.ui.theme.*
import com.hamza.wallpap.util.isOnline
import com.hamza.wallpap.util.saveMediaToStorage
import com.hamza.wallpap.util.shareWallpaper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class
)
@Composable
fun EditorBottomSheet(
    itemList: MutableList<CustomWallpaperBackgroundColor>,
    customWallpaperViewModel: CustomWallpaperViewModel,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    randomItems: LazyPagingItems<UnsplashImage>,
    context: Context,
    singlePhotoPickerLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    isWhatsAppInstalled: Boolean,
) {
    if (customWallpaperViewModel.wallpaperDialogState.value) {
        TextFormatDialog(
            dialogState = customWallpaperViewModel.wallpaperDialogState,
            itemList,
            customWallpaperViewModel
        )
    }

    if (customWallpaperViewModel.colorPickerDialogState.value) {
        ColorPickerDialog(
            dialogState = customWallpaperViewModel.colorPickerDialogState,
            customWallpaperViewModel = customWallpaperViewModel
        )
    }

    var rotationAngle by remember { mutableStateOf(360F) }
    val rotationAnimation by animateFloatAsState(
        targetValue = rotationAngle,
        tween(
            durationMillis = 650,
            easing = FastOutSlowInEasing
        )
    )

    if (!bottomSheetState.isVisible) {
        customWallpaperViewModel.bgColorBottomSheet.value = false
        customWallpaperViewModel.textBottomSheet.value = false
        customWallpaperViewModel.bgImageBottomSheet.value = false
        customWallpaperViewModel.saveImageBottomSheet.value = false
    }

    if (customWallpaperViewModel.bgColorBottomSheet.value) {
        Card(modifier = Modifier.background(MaterialTheme.colors.background)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.background(MaterialTheme.colors.background)
            ) {
                Spacer(modifier = Modifier.padding(vertical = 2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 10.dp)
                ) {
                    Text(
                        textAlign = TextAlign.Start,
                        text = stringResource(id = R.string.bg_color),
                        color = MaterialTheme.colors.topAppBarTitle,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = maven_pro_regular,
                        fontSize = 16.sp
                    )
                    androidx.compose.material3.IconButton(
                        onClick = {
                            scope.launch {
                                bottomSheetState.hide()
                            }
                            customWallpaperViewModel.bgColorBottomSheet.value = false
                            customWallpaperViewModel.textBottomSheet.value = false
                            customWallpaperViewModel.bgImageBottomSheet.value = false
                            customWallpaperViewModel.saveImageBottomSheet.value = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(id = R.string.close),
                            tint = MaterialTheme.colors.topAppBarTitle,
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Card(
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(end = 8.dp, start = 12.dp)
                            .size(40.dp)
                            .clip(CircleShape),
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .border(0.5.dp, MaterialTheme.colors.textColor, CircleShape)
                                .background(
                                    MaterialTheme.colors.bottomAppBarContentColor
                                )
                                .clickable {
                                    customWallpaperViewModel.colorPickerDialogState.value = true
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Colorize,
                                contentDescription = stringResource(id = R.string.bg_color),
                                tint = MaterialTheme.colors.topAppBarTitle,
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .horizontalScroll(rememberScrollState())
                    ) {
                        itemList.forEachIndexed { index, s ->
                            ColorChips(
                                color = s.color,
                                selected = customWallpaperViewModel.selectedBgColorIndex.value == index,
                                onClick = {
                                    customWallpaperViewModel.selectedBgColorIndex.value = index
                                    customWallpaperViewModel.bgBoxColor.value = s.color
                                })
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }

    if (customWallpaperViewModel.textBottomSheet.value) {
        Card(modifier = Modifier.background(MaterialTheme.colors.background)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.background(MaterialTheme.colors.background)
            ) {
                Spacer(modifier = Modifier.padding(vertical = 2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 10.dp)
                ) {
                    Text(
                        textAlign = TextAlign.Start,
                        text = stringResource(id = R.string.text),
                        color = MaterialTheme.colors.topAppBarTitle,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = maven_pro_regular,
                        fontSize = 16.sp
                    )
                    androidx.compose.material3.IconButton(
                        onClick = {
                            scope.launch {
                                bottomSheetState.hide()
                            }
                            customWallpaperViewModel.bgColorBottomSheet.value = false
                            customWallpaperViewModel.textBottomSheet.value = false
                            customWallpaperViewModel.bgImageBottomSheet.value = false
                            customWallpaperViewModel.saveImageBottomSheet.value = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(id = R.string.close),
                            tint = MaterialTheme.colors.topAppBarTitle,
                        )
                    }
                }

                androidx.compose.material3.OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    value = customWallpaperViewModel.wallpaperText.value,
                    singleLine = true,
                    maxLines = 1,
                    onValueChange = {
                        customWallpaperViewModel.wallpaperText.value = it
                    },
                    label = {
                        androidx.compose.material3.Text(
                            stringResource(id = R.string.enter_text),
                            color = MaterialTheme.colors.textColor,
                            fontFamily = maven_pro_regular,
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = MaterialTheme.colors.textColor,
                        focusedBorderColor = MaterialTheme.colors.textColor,
                        unfocusedBorderColor = MaterialTheme.colors.iconColor,
                        textColor = MaterialTheme.colors.textColor
                    ),
                    leadingIcon = {
                        IconButton(
                            modifier = Modifier.rotate(rotationAnimation),
                            onClick = {
                                customWallpaperViewModel.wallpaperText.value = ""
                                rotationAngle += 360f
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.clear_text),
                                tint = MaterialTheme.colors.topAppBarContentColor
                            )
                        }
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                    delay(200)
                                    customWallpaperViewModel.wallpaperDialogState.value = true
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.TextFormat,
                                contentDescription = stringResource(id = R.string.text_style),
                                tint = MaterialTheme.colors.bottomAppBarContentColor,
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }

    if (customWallpaperViewModel.bgImageBottomSheet.value) {
        Card(modifier = Modifier.background(MaterialTheme.colors.background)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.background(MaterialTheme.colors.background)
            ) {
                Spacer(modifier = Modifier.padding(vertical = 2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 10.dp)
                ) {
                    Text(
                        textAlign = TextAlign.Start,
                        text = stringResource(id = R.string.bg_image),
                        color = MaterialTheme.colors.topAppBarTitle,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = maven_pro_regular,
                        fontSize = 16.sp
                    )
                    androidx.compose.material3.IconButton(
                        onClick = {
                            scope.launch {
                                bottomSheetState.hide()
                            }
                            customWallpaperViewModel.bgColorBottomSheet.value = false
                            customWallpaperViewModel.textBottomSheet.value = false
                            customWallpaperViewModel.bgImageBottomSheet.value = false
                            customWallpaperViewModel.saveImageBottomSheet.value = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(id = R.string.close),
                            tint = MaterialTheme.colors.topAppBarTitle,
                        )
                    }
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 6.dp)
                )
                {
                    Card(
                        backgroundColor = MaterialTheme.colors.background,
                        modifier = Modifier
                            .height(120.dp)
                            .width(80.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colors.topAppBarTitle,
                                shape = RoundedCornerShape(4.dp)
                            ),
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .height(120.dp)
                                .width(80.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.addanimagereduced),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                            FilledTonalIconButton(
                                onClick = {
                                    singlePhotoPickerLauncher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                },
                                colors = IconButtonDefaults.filledTonalIconButtonColors(
                                    containerColor = MaterialTheme.colors.bottomAppBarContentColor
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddAPhoto,
                                    contentDescription = stringResource(id = R.string.add_photo),
                                    tint = Color.White,
                                )
                            }
                        }
                    }

                    if (isOnline(context)) {
                        LazyRow(
                            modifier = Modifier.padding(start = 8.dp, end = 4.dp),
                            content = {
                                items(randomItems.itemCount) {
                                    randomItems[it]?.let { unsplashImage ->
                                        BackgroundImageListItem(
                                            unsplashImage,
                                            customWallpaperViewModel,
                                            context
                                        )
                                    }
                                }
                            })
                    } else {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(id = R.string.connect_internet) + "\n" + stringResource(
                                    id = R.string.online_wallpapers
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontFamily = maven_pro_regular
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier.padding(6.dp))

                Text(
                    textAlign = TextAlign.Start,
                    text = stringResource(id = R.string.img_clarity),
                    color = MaterialTheme.colors.topAppBarTitle,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = maven_pro_regular,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp)
                )

                androidx.compose.material3.Slider(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
                    value = customWallpaperViewModel.imageTransparencySliderPosition.value,
                    onValueChange = {
                        customWallpaperViewModel.imageTransparencySliderPosition.value = it
                    },
                    valueRange = 0f..1f,
                    onValueChangeFinished = {
                        customWallpaperViewModel.bgImageTransparency.value =
                            customWallpaperViewModel.imageTransparencySliderPosition.value
                    },
                    colors = androidx.compose.material3.SliderDefaults.colors(
                        activeTrackColor = MaterialTheme.colors.bottomAppBarContentColor.copy(0.5f),
                        thumbColor = MaterialTheme.colors.bottomAppBarContentColor
                    )
                )

                Text(
                    textAlign = TextAlign.Start,
                    text = stringResource(id = R.string.img_style),
                    color = MaterialTheme.colors.topAppBarTitle,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = maven_pro_regular,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 10.dp)
                ) {

                    if (customWallpaperViewModel.contentScale.value == ContentScale.Crop) {
                        IconButton(
                            onClick = {
                                customWallpaperViewModel.contentScale.value = ContentScale.Fit
                            }) {
                            Icon(
                                imageVector = Icons.Default.ZoomInMap,
                                contentDescription = stringResource(id = R.string.zoom_in_img),
                                tint = MaterialTheme.colors.topAppBarContentColor
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {
                                customWallpaperViewModel.contentScale.value = ContentScale.Crop
                            }) {
                            Icon(
                                imageVector = Icons.Default.ZoomOutMap,
                                contentDescription = stringResource(id = R.string.zoom_out_img),
                                tint = MaterialTheme.colors.topAppBarContentColor
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            customWallpaperViewModel.imageRotate.value -= 90f
                        }) {
                        Icon(
                            imageVector = Icons.Default.Rotate90DegreesCcw,
                            contentDescription = stringResource(id = R.string.rotate_ccw),
                            tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }

                    IconButton(
                        onClick = {
                            customWallpaperViewModel.imageRotate.value += 90f
                        }) {
                        Icon(
                            imageVector = Icons.Default.Rotate90DegreesCw,
                            contentDescription = stringResource(id = R.string.rotate_cw),
                            tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }

                    IconButton(
                        modifier = Modifier.rotate(rotationAnimation),
                        onClick = {
                            rotationAngle += 360f
                            customWallpaperViewModel.bgImageFullUrl.value = null
                            customWallpaperViewModel.bgImageRegularUrl.value = null
                            customWallpaperViewModel.bgImageUri.value = null
                        }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.delete_img),
                            tint = MaterialTheme.colors.topAppBarContentColor
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 6.dp))
            }

            Spacer(modifier = Modifier.padding(vertical = 6.dp))
        }
    }

    if (customWallpaperViewModel.saveImageBottomSheet.value) {
        Card(modifier = Modifier.background(MaterialTheme.colors.background)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.background(MaterialTheme.colors.background)
            ) {
                Spacer(modifier = Modifier.padding(vertical = 2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 10.dp)
                ) {
                    Text(
                        textAlign = TextAlign.Start,
                        text = stringResource(id = R.string.download_and_share),
                        color = MaterialTheme.colors.topAppBarTitle,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = maven_pro_regular,
                        fontSize = 16.sp
                    )
                    androidx.compose.material3.IconButton(
                        onClick = {
                            scope.launch {
                                bottomSheetState.hide()
                            }
                            customWallpaperViewModel.bgColorBottomSheet.value = false
                            customWallpaperViewModel.saveImageBottomSheet.value = false
                            customWallpaperViewModel.bgImageBottomSheet.value = false
                            customWallpaperViewModel.bgColorBottomSheet.value = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(id = R.string.close),
                            tint = MaterialTheme.colors.topAppBarTitle,
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 2.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    /*
                    Row(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 2.dp)
                            .border(
                                border = BorderStroke(1.dp, MaterialTheme.colors.topAppBarContentColor),
                                shape = RoundedCornerShape(2.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "Image Format",
                            color = MaterialTheme.colors.topAppBarTitle,
                            fontWeight = FontWeight.Normal,
                            fontFamily = maven_pro_regular,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 10.dp)
                        )

                        Row(modifier = Modifier.selectableGroup(), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "PNG",
                                color = MaterialTheme.colors.topAppBarTitle,
                                fontWeight = FontWeight.Normal,
                                fontFamily = maven_pro_regular,
                                fontSize = 13.sp,
                            )
                            RadioButton(
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colors.bottomAppBarContentColor,
                                    unselectedColor = MaterialTheme.colors.topAppBarContentColor
                                ),
                                selected = radioState,
                                onClick = { radioState = true },
                                modifier = Modifier.semantics { contentDescription = "Localized Description" }
                            )

                            Text(
                                text = "JPEG",
                                color = MaterialTheme.colors.topAppBarTitle,
                                fontWeight = FontWeight.Normal,
                                fontFamily = maven_pro_regular,
                                fontSize = 13.sp,
                            )
                            RadioButton(
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colors.bottomAppBarContentColor,
                                    unselectedColor = MaterialTheme.colors.topAppBarContentColor
                                ),
                                selected = !radioState,
                                onClick = { radioState = false },
                                modifier = Modifier.semantics { contentDescription = "Localized Description" }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    */

                    Row(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(5.dp))
                            .clickable {
                                customWallpaperViewModel.savedImageBitmap.value?.let {
                                    saveMediaToStorage(
                                        it,
                                        context
                                    )
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = stringResource(id = R.string.download),
                            tint = MaterialTheme.colors.topAppBarContentColor,
                            modifier = Modifier.padding(4.dp)
                        )

                        Spacer(modifier = Modifier.padding(horizontal = 6.dp))

                        Text(
                            text = stringResource(id = R.string.download),
                            color = MaterialTheme.colors.topAppBarTitle,
                            fontWeight = FontWeight.Normal,
                            fontFamily = maven_pro_regular,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(5.dp))
                            .clickable {
                                shareWallpaper(
                                    context,
                                    customWallpaperViewModel.savedImageBitmap.value,
                                    false,
                                    saveToDrive = true
                                )
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddToDrive,
                            contentDescription = stringResource(id = R.string.save_to_drive),
                            tint = Color(0xFFffbb00),
                            modifier = Modifier.padding(4.dp)
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 6.dp))
                        Text(
                            text = stringResource(id = R.string.save_to_drive),
                            color = MaterialTheme.colors.topAppBarTitle,
                            fontWeight = FontWeight.Normal,
                            fontFamily = maven_pro_regular,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(5.dp))
                            .clickable {
                                if (isWhatsAppInstalled) {
                                    shareWallpaper(
                                        context,
                                        customWallpaperViewModel.savedImageBitmap.value,
                                        shareWithWhatsAppOnly = true,
                                        saveToDrive = false
                                    )
                                } else {
                                    val playStoreIntent = Intent(Intent.ACTION_VIEW)
                                    playStoreIntent.data =
                                        Uri.parse("market://details?id=com.whatsapp")
                                    context.startActivity(playStoreIntent)
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Default.Whatsapp,
                            contentDescription = stringResource(id = R.string.whatsapp),
                            tint = Color(0xFF25D366),
                            modifier = Modifier.padding(4.dp)
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 6.dp))
                        Text(
                            text = stringResource(id = R.string.whatsapp),
                            color = MaterialTheme.colors.topAppBarTitle,
                            fontWeight = FontWeight.Normal,
                            fontFamily = maven_pro_regular,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(5.dp))
                            .clickable {
                                shareWallpaper(
                                    context,
                                    customWallpaperViewModel.savedImageBitmap.value,
                                    shareWithWhatsAppOnly = false,
                                    saveToDrive = false
                                )
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreHoriz,
                            contentDescription = stringResource(id = R.string.more_apps),
                            tint = MaterialTheme.colors.topAppBarContentColor,
                            modifier = Modifier.padding(4.dp)
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 6.dp))
                        Text(
                            text = stringResource(id = R.string.more_apps),
                            color = MaterialTheme.colors.topAppBarTitle,
                            fontWeight = FontWeight.Normal,
                            fontFamily = maven_pro_regular,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}


