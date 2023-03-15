package com.hamza.wallpap.ui.screens.editor

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.hamza.wallpap.model.CustomWallpaperBackgroundColor
import com.hamza.wallpap.model.UnsplashImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditorBottomSheet(
    navController: NavHostController,
    itemList: MutableList<CustomWallpaperBackgroundColor>,
    customWallpaperViewModel: CustomWallpaperViewModel,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    randomItems: LazyPagingItems<UnsplashImage>,
    context: Context,
) {

    if (customWallpaperViewModel.wallpaperDialogState.value) {
        TextFormatDialog(
            dialogState = customWallpaperViewModel.wallpaperDialogState,
            context = context,
            itemList,
            customWallpaperViewModel
        )
    }

    if (!bottomSheetState.isVisible) {
        customWallpaperViewModel.bgColorBottomSheet.value = false
        customWallpaperViewModel.textBottomSheet.value = false
        customWallpaperViewModel.bgColorBottomSheet.value = false
    }
    if (customWallpaperViewModel.bgColorBottomSheet.value) {
        Surface {
            Card {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
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
                            text = "Background Color",
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold,
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
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null)
                        }
                    }

                    Spacer(modifier = Modifier.padding(vertical = 2.dp))

                    LazyRow(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        items(itemList) { item ->
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, Color.Black, CircleShape)
                                    .background(item.color)
                                    .clickable {
                                        customWallpaperViewModel.bgImageUrl.value = null
                                        customWallpaperViewModel.boxColor.value = item.color
                                    }
                            )
                            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                        }
                    }
                    Spacer(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }

    if (customWallpaperViewModel.textBottomSheet.value) {
        Card {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
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
                        text = "Custom Text",
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold,
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
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }

                androidx.compose.material3.OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    value = customWallpaperViewModel.wallpaperText.value,
                    singleLine = true,
                    maxLines = 1,
                    onValueChange = { customWallpaperViewModel.wallpaperText.value = it },
                    label = {
                        androidx.compose.material3.Text("Enter text here")
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = Color.Black
                    ),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                customWallpaperViewModel.wallpaperDialogState.value = true
                            }
                        ) {
                            Icon(imageVector = Icons.Default.TextFormat, contentDescription = null)
                        }
                    }
                )

//                Text(
//                    textAlign = TextAlign.Start,
//                    text = "Text Opacity",
//                    color = Color.Black,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 12.sp,
//                    modifier = Modifier.fillMaxWidth()
//                    .padding(top = 10.dp, start = 12.dp, end = 10.dp)
//                )
//
//                Slider(
//                    modifier = Modifier.padding(20.dp),
//                    value = textSliderPosition,
//                    onValueChange = { textSliderPosition = it },
//                    valueRange = 12f..100f,
//                    onValueChangeFinished = {
//                        customWallpaperViewModel.wallpaperTextSize.value = textSliderPosition.sp
//                    }
//                )
                Spacer(modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }

    if (customWallpaperViewModel.bgImageBottomSheet.value) {
        Card {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
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
                        text = "Background Image",
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold,
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
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }

                LazyRow(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    content = {
                        items(randomItems.itemCount) {
                            randomItems[it]?.let { unsplashImage ->
                                BackgroundImageListItem(unsplashImage, customWallpaperViewModel)
                            }
                        }
                    })

                Spacer(modifier = Modifier.padding(6.dp))

                Text(
                    textAlign = TextAlign.Start,
                    text = "Image Opacity",
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp)
                )

                Slider(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 0.dp),
                    value = customWallpaperViewModel.transparencySliderPosition.value,
                    onValueChange = {
                        customWallpaperViewModel.transparencySliderPosition.value = it
                    },
                    valueRange = 0.3f..1f,
                    onValueChangeFinished = {
                        customWallpaperViewModel.bgImageTransparency.value =
                            customWallpaperViewModel.transparencySliderPosition.value
                    }
                )

                Spacer(modifier = Modifier.padding(vertical = 6.dp))
            }
        }
    }
}