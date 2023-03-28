package com.hamza.wallpap.ui.screens.editor

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hamza.wallpap.model.CustomWallpaperBackgroundColor
import com.hamza.wallpap.ui.theme.bottomAppBarContentColor
import com.hamza.wallpap.ui.theme.iconColor
import com.hamza.wallpap.ui.theme.textColor

data class FontFamilySearchChip(val fontTitle: String, val font: FontFamily)

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
        shape = RoundedCornerShape(20.dp),
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
                color = MaterialTheme.colors.textColor,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp)
            )

            Spacer(modifier = Modifier.padding(2.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 6.dp, vertical = 6.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                itemList.forEachIndexed { index, s ->
                    ColorChips(
                        color = s.color,
                        selected = customWallpaperViewModel.selectedTextColorIndex.value == index,
                        onClick = {
                            customWallpaperViewModel.selectedTextColorIndex.value = index
                            customWallpaperViewModel.colorItem.value = s.color
                            customWallpaperViewModel.wallpaperTextColor.value = s.color
                        })
                }
            }


            Spacer(modifier = Modifier.padding(6.dp))

            Text(
                textAlign = TextAlign.Start,
                text = "Font Size",
                color = MaterialTheme.colors.textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp)
            )

            androidx.compose.material3.Slider(
                modifier = Modifier.padding(horizontal = 10.dp),
                value = customWallpaperViewModel.textSliderPosition.value,
                onValueChange = { customWallpaperViewModel.textSliderPosition.value = it },
                valueRange = 12f..100f,
                onValueChangeFinished = {
                    customWallpaperViewModel.wallpaperTextSize.value =
                        customWallpaperViewModel.textSliderPosition.value.sp
                },
                colors = androidx.compose.material3.SliderDefaults.colors(
                    activeTrackColor = MaterialTheme.colors.bottomAppBarContentColor.copy(0.5f),
                    thumbColor = MaterialTheme.colors.bottomAppBarContentColor
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    textAlign = TextAlign.Start,
                    text = "Font Family",
                    color = MaterialTheme.colors.textColor,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )

                Card(
                    backgroundColor =
                    if (customWallpaperViewModel.wallpaperTextColor.value == Color(0xFF000000)) Color.White
                    else if (customWallpaperViewModel.wallpaperTextColor.value == Color(0xFFFFFFFF)) Color.Black
                    else MaterialTheme.colors.background
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        textAlign = TextAlign.Start,
                        text = customWallpaperViewModel.fontFamilyName.value,
                        style = TextStyle(
                            fontStyle = customWallpaperViewModel.wallpaperTextFontStyle.value,
                            color = customWallpaperViewModel.wallpaperTextColor.value,
                            fontSize = 14.sp,
                            textDecoration = customWallpaperViewModel.wallpaperTextDecoration.value,
                            fontWeight = customWallpaperViewModel.wallpaperTextFontWeight.value,
                            textAlign = customWallpaperViewModel.wallpaperTextAlign.value,
                            fontFamily = customWallpaperViewModel.textFontFamily.value
                        )
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 6.dp, vertical = 6.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                customWallpaperViewModel.fontFamilyItems.forEachIndexed { index, s ->
                    FontFamilySearchChips(
                        text = s.fontTitle,
                        selected = customWallpaperViewModel.selectedFontFamilyIndex.value == index
                    ) {
                        customWallpaperViewModel.fontFamilyName.value = s.fontTitle
                        customWallpaperViewModel.selectedFontFamilyIndex.value = index
                        customWallpaperViewModel.textFontFamily.value = s.font
                    }
                    Spacer(modifier = Modifier.padding(horizontal = 6.dp))
                }
            }

            Text(
                textAlign = TextAlign.Start,
                text = "Font Style",
                color = MaterialTheme.colors.textColor,
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
                            tint = MaterialTheme.colors.bottomAppBarContentColor
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.FormatAlignCenter,
                            contentDescription = null,
                            tint = MaterialTheme.colors.iconColor
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
                            tint = MaterialTheme.colors.bottomAppBarContentColor
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.FormatAlignJustify,
                            contentDescription = null,
                            tint = MaterialTheme.colors.iconColor
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
                            tint = MaterialTheme.colors.bottomAppBarContentColor
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.FormatAlignRight,
                            contentDescription = null,
                            tint = MaterialTheme.colors.iconColor
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
                            tint = MaterialTheme.colors.bottomAppBarContentColor
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.FormatAlignLeft,
                            contentDescription = null,
                            tint = MaterialTheme.colors.iconColor
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {

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
                            tint = MaterialTheme.colors.bottomAppBarContentColor
                        )
                    } else {
                        customWallpaperViewModel.wallpaperTextFontWeight.value = FontWeight.Normal
                        Icon(
                            imageVector = Icons.Default.FormatBold,
                            contentDescription = null,
                            tint = MaterialTheme.colors.iconColor
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
                            tint = MaterialTheme.colors.bottomAppBarContentColor
                        )
                    } else {
                        customWallpaperViewModel.wallpaperTextFontStyle.value = FontStyle.Normal
                        Icon(
                            imageVector = Icons.Default.FormatItalic,
                            contentDescription = null,
                            tint = MaterialTheme.colors.iconColor
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

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
                            tint = MaterialTheme.colors.bottomAppBarContentColor
                        )
                    } else {
                        customWallpaperViewModel.wallpaperTextDecoration.value = TextDecoration.None
                        Icon(
                            imageVector = Icons.Default.FormatStrikethrough,
                            contentDescription = null,
                            tint = MaterialTheme.colors.iconColor
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                TextButton(
                    onClick = { dialogState.value = false }
                ) {
                    Text(text = "Done", color = MaterialTheme.colors.textColor)
                }
            }
        }
    }
}

@Composable
fun TextColorListItem(
    color: Color,
    customWallpaperViewModel: CustomWallpaperViewModel,
) {

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
                .border(1.dp, MaterialTheme.colors.textColor, CircleShape)
                .background(color)
                .clickable {
                    customWallpaperViewModel.wallpaperTextColor.value = color
                }
        )
    }
}
