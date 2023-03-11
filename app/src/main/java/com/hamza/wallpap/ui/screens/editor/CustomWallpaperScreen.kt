package com.hamza.wallpap.ui.screens.editor

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.ExperimentalMaterial3Api
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class ItemList(val color: Color, val size: Int)

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CustomWallpaperScreen(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    customWallpaperViewModel: CustomWallpaperViewModel,
) {
    val itemList = mutableListOf<ItemList>()
    itemList.add(ItemList(Color(0xFFFFFFFF), 8))
    itemList.add(ItemList(Color(0xFF485049), 8))
    itemList.add(ItemList(Color(0xFF41FDF7), 8))
    itemList.add(ItemList(Color(0xFFFF5722), 8))
    itemList.add(ItemList(Color(0xFFE91E63), 8))
    itemList.add(ItemList(Color(0xFF8BC34A), 8))
    itemList.add(ItemList(Color(0xFF3F51B5), 8))
    itemList.add(ItemList(Color(0xFF9C27B0), 8))

    var bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    var wallpaperText by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
        sheetState = bottomSheetState,
        sheetContent = {
            EditorBottomSheet(
                navController,
                itemList,
                customWallpaperViewModel,
                bottomSheetState,
                scope
            )
        }, content = {
            androidx.compose.material3.Scaffold(
                bottomBar = {
                    androidx.compose.material3.BottomAppBar(
                        floatingActionButton = {
                            androidx.compose.material3.SmallFloatingActionButton(
                                shape = RoundedCornerShape(4.dp),
                                onClick = { /*TODO*/ }) {
                                Icon(Icons.Default.Add, contentDescription = null)
                            }
                        },
                        actions = {
                            androidx.compose.material3.IconButton(
                                onClick = {
                                    scope.launch { bottomSheetState.show() }
                                }) {
                                Icon(
                                    Icons.Outlined.Palette,
                                    contentDescription = null
                                )
                            }
                            androidx.compose.material3.IconButton(onClick = { /*TODO*/ }) {
                                Icon(Icons.Default.TextFields, contentDescription = null)
                            }
                            androidx.compose.material3.IconButton(onClick = { /*TODO*/ }) {
                                Icon(Icons.Default.Image, contentDescription = null)
                            }
                        }
                    )
                }) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.padding(vertical = 22.dp))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(300.dp)
                            .height(600.dp)
                            .border(border = BorderStroke(1.dp, Color.Black))
                            .background(customWallpaperViewModel.boxColor.value)
                    ) {
                        Text(text = wallpaperText, color = Color.Black, fontSize = 22.sp)
                    }
                }
            }
        })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditorBottomSheet(
    navController: NavHostController,
    itemList: MutableList<ItemList>,
    customWallpaperViewModel: CustomWallpaperViewModel,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
) {
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
                                    customWallpaperViewModel.boxColor.value = item.color
                                }
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                    }

                }
                Spacer(modifier = Modifier.padding(vertical = 6.dp))
            }
        }
    }
}
