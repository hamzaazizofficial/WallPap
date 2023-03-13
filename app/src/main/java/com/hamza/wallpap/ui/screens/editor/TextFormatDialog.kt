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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hamza.wallpap.model.CustomWallpaperBackgroundColor

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun TextFormatDialog(
    dialogState: MutableState<Boolean>,
    context: Context,
    itemList: MutableList<CustomWallpaperBackgroundColor>,
    customWallpaperViewModel: CustomWallpaperViewModel,
) {
    Dialog(
        onDismissRequest = { dialogState.value = false },
        properties = DialogProperties(usePlatformDefaultWidth = true),
    ) {
        TextFormatDialogUI(
            modifier = Modifier,
            dialogState,
            context,
            itemList,
            customWallpaperViewModel
        )
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun TextFormatDialogUI(
    modifier: Modifier = Modifier,
    dialogState: MutableState<Boolean>,
    context: Context,
    itemList: MutableList<CustomWallpaperBackgroundColor>,
    customWallpaperViewModel: CustomWallpaperViewModel,
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 10.dp, 10.dp, 10.dp),
        elevation = 4.dp,
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background)
        ) {
            Spacer(modifier = Modifier.padding(6.dp))

            Text(
                textAlign = TextAlign.Start,
                text = "Font Color",
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp)
            )

            Spacer(modifier = Modifier.padding(2.dp))

            LazyRow(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                content = {
                    items(itemList) {
                        TextColorListItem(color = it.color, customWallpaperViewModel)
                    }
                })

            Spacer(modifier = Modifier.padding(6.dp))

            Text(
                textAlign = TextAlign.Start,
                text = "Font Size",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp)
            )

            Slider(
                modifier = Modifier.padding(horizontal = 10.dp),
                value = customWallpaperViewModel.textSliderPosition.value,
                onValueChange = { customWallpaperViewModel.textSliderPosition.value = it },
                valueRange = 12f..100f,
                onValueChangeFinished = {
                    customWallpaperViewModel.wallpaperTextSize.value = customWallpaperViewModel.textSliderPosition.value.sp
                }
            )

            Text(
                textAlign = TextAlign.Start,
                text = "Text Style",
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                IconToggleButton(
                    checked = customWallpaperViewModel.textAlignCenterChecked.value,
                    onCheckedChange = {
                        customWallpaperViewModel.textAlignCenterChecked.value = it
                    }) {
                    if (customWallpaperViewModel.textAlignCenterChecked.value) {
                        customWallpaperViewModel.wallpaperTextAlign.value = TextAlign.Center
                        Icon(
                            imageVector = Icons.Filled.FormatAlignCenter,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.FormatAlignCenter,
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                IconToggleButton(
                    checked = customWallpaperViewModel.textAlignJustifyChecked.value,
                    onCheckedChange = {
                        customWallpaperViewModel.textAlignJustifyChecked.value = it
                    }) {
                    if (customWallpaperViewModel.textAlignJustifyChecked.value) {
                        customWallpaperViewModel.wallpaperTextAlign.value = TextAlign.Justify
                        Icon(
                            imageVector = Icons.Filled.FormatAlignJustify,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.FormatAlignJustify,
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                IconToggleButton(
                    checked = customWallpaperViewModel.textAlignRightChecked.value,
                    onCheckedChange = {
                        customWallpaperViewModel.textAlignRightChecked.value = it
                    }) {
                    if (customWallpaperViewModel.textAlignRightChecked.value) {
                        customWallpaperViewModel.wallpaperTextAlign.value = TextAlign.Right
                        Icon(
                            imageVector = Icons.Filled.FormatAlignRight,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.FormatAlignRight,
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                IconToggleButton(
                    checked = customWallpaperViewModel.textAlignLeftChecked.value,
                    onCheckedChange = {
                        customWallpaperViewModel.textAlignLeftChecked.value = it
                    }) {
                    if (customWallpaperViewModel.textAlignLeftChecked.value) {
                        customWallpaperViewModel.wallpaperTextAlign.value = TextAlign.Left
                        Icon(
                            imageVector = Icons.Filled.FormatAlignLeft,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.FormatAlignLeft,
                            contentDescription = null
                        )
                    }
                }
            }

//            Text(
//                textAlign = TextAlign.Start,
//                text = "Text Weight",
//                color = Color.Black,
//                fontWeight = FontWeight.ExtraBold,
//                fontSize = 16.sp,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 12.dp)
//            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
//                IconButton(
//                    onClick = {
//                        customWallpaperViewModel.wallpaperTextFontWeight.value = FontWeight.Bold
//                    }) {
//                    Icon(imageVector = Icons.Default.FormatBold, contentDescription = null)
//                }

                IconToggleButton(
                    checked = customWallpaperViewModel.textFontBoldChecked.value,
                    onCheckedChange = {
                        customWallpaperViewModel.textFontBoldChecked.value = it
                    }) {
                    if (customWallpaperViewModel.textFontBoldChecked.value) {
                        customWallpaperViewModel.wallpaperTextFontWeight.value = FontWeight.Bold
                        Icon(
                            imageVector = Icons.Filled.FormatBold,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    } else {
                        customWallpaperViewModel.wallpaperTextFontWeight.value = FontWeight.Normal
                        Icon(
                            imageVector = Icons.Default.FormatBold,
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                IconToggleButton(
                    checked = customWallpaperViewModel.textFontItalicChecked.value,
                    onCheckedChange = {
                        customWallpaperViewModel.textFontItalicChecked.value = it
                    }) {
                    if (customWallpaperViewModel.textFontItalicChecked.value) {
                        customWallpaperViewModel.wallpaperTextFontStyle.value = FontStyle.Italic
                        Icon(
                            imageVector = Icons.Filled.FormatItalic,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    } else {
                        customWallpaperViewModel.wallpaperTextFontStyle.value = FontStyle.Normal
                        Icon(
                            imageVector = Icons.Default.FormatItalic,
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

//                IconToggleButton(
//                    checked = customWallpaperViewModel.textFontUnderlinedChecked.value,
//                    onCheckedChange = {
//                        customWallpaperViewModel.textFontUnderlinedChecked.value = it
//                    }) {
//                    if (customWallpaperViewModel.textFontUnderlinedChecked.value) {
//                        customWallpaperViewModel.wallpaperTextDecoration.value = TextDecoration.Underline
//                        Icon(
//                            imageVector = Icons.Filled.FormatUnderlined,
//                            contentDescription = null,
//                            tint = Color.Red
//                        )
//                    } else {
//                        customWallpaperViewModel.wallpaperTextDecoration.value = TextDecoration.None
//                        Icon(
//                            imageVector = Icons.Default.FormatUnderlined,
//                            contentDescription = null
//                        )
//                    }
//                }
//
//                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                IconToggleButton(
                    checked = customWallpaperViewModel.textFontStrikethroughChecked.value,
                    onCheckedChange = {
                        customWallpaperViewModel.textFontStrikethroughChecked.value = it
                    }) {
                    if (customWallpaperViewModel.textFontStrikethroughChecked.value) {
                        customWallpaperViewModel.wallpaperTextDecoration.value =
                            TextDecoration.LineThrough
                        Icon(
                            imageVector = Icons.Filled.FormatStrikethrough,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    } else {
                        customWallpaperViewModel.wallpaperTextDecoration.value = TextDecoration.None
                        Icon(
                            imageVector = Icons.Default.FormatStrikethrough,
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

//                IconButton(
//                    onClick = {
//                        customWallpaperViewModel.wallpaperTextFontStyle.value = FontStyle.Italic
//                    }) {
//                    Icon(imageVector = Icons.Default.FormatItalic, contentDescription = null)
//                }
//
//                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
//
//                IconButton(
//                    onClick = {
//                        customWallpaperViewModel.wallpaperTextDecoration.value =
//                            TextDecoration.Underline
//                    }) {
//                    Icon(imageVector = Icons.Default.FormatUnderlined, contentDescription = null)
//                }
//
//                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
//
//                IconButton(
//                    onClick = {
//                        customWallpaperViewModel.wallpaperTextDecoration.value =
//                            TextDecoration.LineThrough
//                    }) {
//                    Icon(imageVector = Icons.Default.FormatStrikethrough, contentDescription = null)
//                }
            }

//            var checked by remember { mutableStateOf(false) }
//            IconToggleButton(checked = checked, onCheckedChange = { checked = it }) {
//                if (checked) {
//                    Icon(Icons.Filled.Lock, contentDescription = "Localized description")
//                } else {
//                    Icon(Icons.Outlined.Lock, contentDescription = "Localized description")
//                }
//            }

//            Spacer(modifier = Modifier.padding(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            ) {
                TextButton(
                    onClick = { dialogState.value = false }
                ) {
                    Text(text = "Done")
//                    Icon(imageVector = Icons.Default.Done, contentDescription = null)
                }
            }

//            Spacer(modifier = Modifier.padding(6.dp))
        }
    }
}

@Composable
fun TextColorListItem(color: Color, customWallpaperViewModel: CustomWallpaperViewModel) {
    Card(
        shape = CircleShape,
        modifier = Modifier
            .padding(6.dp)
            .size(40.dp)
            .clip(CircleShape),
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Black, CircleShape)
                .background(color)
                .clickable {
                    customWallpaperViewModel.wallpaperTextColor.value = color
                }
        )
    }
}
