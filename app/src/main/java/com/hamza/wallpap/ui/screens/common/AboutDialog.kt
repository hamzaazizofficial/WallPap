package com.hamza.wallpap.ui.screens.common


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.stringResource
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
import com.hamza.wallpap.ui.theme.bottomAppBarBackgroundColor
import com.hamza.wallpap.ui.theme.iconColor
import com.hamza.wallpap.ui.theme.maven_pro_regular
import com.hamza.wallpap.ui.theme.textColor

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AboutDialog(
    dialogState: MutableState<Boolean>,
) {
    Dialog(
        onDismissRequest = { dialogState.value = false },
        properties = DialogProperties(usePlatformDefaultWidth = true),
    ) {
        AboutDialogUI(
            modifier = Modifier,
            dialogState
        )
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AboutDialogUI(
    modifier: Modifier = Modifier,
    dialogState: MutableState<Boolean>,
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
                            .size(60.dp)
                    )

                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colors.iconColor,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = maven_pro_regular
                                )
                            ) {
                                append(stringResource(id = R.string.wall))
                            }

                            withStyle(
                                style = SpanStyle(
                                    color = Color(0xFFfe6c40),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = maven_pro_regular
                                )
                            ) {
                                append(stringResource(id = R.string.pap))
                            }
                        }
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
                                    append(stringResource(id = R.string.wallpap_slogan))
                                }
                            })
                    }
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(
                        color = MaterialTheme.colors.bottomAppBarBackgroundColor
                    )
                    .border(BorderStroke(1.dp, MaterialTheme.colors.textColor)),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                TextButton(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .width(80.dp),
                    onClick = {
                        dialogState.value = false
                    }) {
                    Text(
                        text = stringResource(id = R.string.close),
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
