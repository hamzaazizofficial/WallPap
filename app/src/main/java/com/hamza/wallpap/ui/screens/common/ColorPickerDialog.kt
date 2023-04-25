package com.hamza.wallpap.ui.screens.common

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hamza.wallpap.ui.screens.editor.CustomWallpaperViewModel
import com.hamza.wallpap.ui.theme.textColor

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ColorPickerDialog(
    dialogState: MutableState<Boolean>,
    context: Context,
    customWallpaperViewModel: CustomWallpaperViewModel,
) {
    Dialog(
        onDismissRequest = { dialogState.value = false },
        properties = DialogProperties(usePlatformDefaultWidth = true),
    ) {
        ColorPickerDialogUI(
            modifier = Modifier,
            dialogState,
            context,
            customWallpaperViewModel
        )
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ColorPickerDialogUI(
    modifier: Modifier = Modifier,
    dialogState: MutableState<Boolean>,
    context: Context,
    customWallpaperViewModel: CustomWallpaperViewModel,
) {
    val colorPickerBoxColor by remember(
        customWallpaperViewModel.redColorValue.value,
        customWallpaperViewModel.greenColorValue.value,
        customWallpaperViewModel.blueColorValue.value,
        customWallpaperViewModel.alphaColorValue.value
    ) {
        mutableStateOf(
            Color(
                customWallpaperViewModel.redColorValue.value,
                customWallpaperViewModel.greenColorValue.value,
                customWallpaperViewModel.blueColorValue.value,
                customWallpaperViewModel.alphaColorValue.value
            )
        )
    }

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
                    Text(
                        text = "Red (${customWallpaperViewModel.redColorValue.value})",
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colors.textColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    )

                    androidx.compose.material3.Slider(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        value = customWallpaperViewModel.redColorSliderPosition.value,
                        onValueChange = {
                            customWallpaperViewModel.redColorSliderPosition.value = it
                        },
                        valueRange = 0f..255f,
                        onValueChangeFinished = {
                            customWallpaperViewModel.redColorValue.value =
                                customWallpaperViewModel.redColorSliderPosition.value.toInt()
                        },
                        colors = androidx.compose.material3.SliderDefaults.colors(
                            activeTrackColor = Color.Red.copy(0.5f),
                            thumbColor = Color.Red
                        )
                    )

                    Text(
                        text = "Green (${customWallpaperViewModel.greenColorValue.value})",
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colors.textColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    )

                    androidx.compose.material3.Slider(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        value = customWallpaperViewModel.greenColorSliderPosition.value,
                        onValueChange = {
                            customWallpaperViewModel.greenColorSliderPosition.value = it
                        },
                        valueRange = 0f..255f,
                        onValueChangeFinished = {
                            customWallpaperViewModel.greenColorValue.value =
                                customWallpaperViewModel.greenColorSliderPosition.value.toInt()
                        },
                        colors = androidx.compose.material3.SliderDefaults.colors(
                            activeTrackColor = Color.Green.copy(0.5f),
                            thumbColor = Color.Green
                        )
                    )

                    Text(
                        text = "Blue (${customWallpaperViewModel.blueColorValue.value})",
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colors.textColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    )

                    androidx.compose.material3.Slider(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        value = customWallpaperViewModel.blueColorSliderPosition.value,
                        onValueChange = {
                            customWallpaperViewModel.blueColorSliderPosition.value = it
                        },
                        valueRange = 0f..255f,
                        onValueChangeFinished = {
                            customWallpaperViewModel.blueColorValue.value =
                                customWallpaperViewModel.blueColorSliderPosition.value.toInt()
                        },
                        colors = androidx.compose.material3.SliderDefaults.colors(
                            activeTrackColor = Color.Blue.copy(0.5f),
                            thumbColor = Color.Blue
                        )
                    )

                    Text(
                        text = "Alpha (${customWallpaperViewModel.alphaColorValue.value})",
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colors.textColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    )

                    androidx.compose.material3.Slider(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        value = customWallpaperViewModel.alphaColorSliderPosition.value,
                        onValueChange = {
                            customWallpaperViewModel.alphaColorSliderPosition.value = it
                        },
                        valueRange = 0f..255f,
                        onValueChangeFinished = {
                            customWallpaperViewModel.alphaColorValue.value =
                                customWallpaperViewModel.alphaColorSliderPosition.value.toInt()
                        },
                        colors = androidx.compose.material3.SliderDefaults.colors(
                            activeTrackColor = Color.Black.copy(0.5f),
                            thumbColor = Color.Black
                        )
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(10.dp)
                            .background(colorPickerBoxColor)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                TextButton(
                    onClick = {
                        customWallpaperViewModel.bgBoxColor.value = colorPickerBoxColor
                        customWallpaperViewModel.selectedBgColorIndex.value = 50
                        dialogState.value = false
                    }
                ) {
                    Text(text = "Pick", color = MaterialTheme.colors.textColor)
                }
            }
        }
    }
}