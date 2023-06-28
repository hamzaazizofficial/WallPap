package com.hamza.wallpap.ui.screens.common


import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.core.content.ContextCompat
import com.hamza.wallpap.R
import com.hamza.wallpap.ui.theme.*

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun GetProDialog(
    dialogState: MutableState<Boolean>,
    context: Context,
) {
    Dialog(
        onDismissRequest = { dialogState.value = false },
        properties = DialogProperties(usePlatformDefaultWidth = true),
    ) {
        GetProDialogUI(
            modifier = Modifier,
            dialogState,
            context
        )
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun GetProDialogUI(
    modifier: Modifier = Modifier,
    dialogState: MutableState<Boolean>,
    context: Context,
) {
    BoxWithConstraints {
        constraints
        when {
            maxHeight <= 370.dp -> {
                /* Small Card will show for phones in landscape orientation */
                Card(
                    shape = RoundedCornerShape(10.dp),
                    modifier = modifier.padding(8.dp, 4.dp, 8.dp, 8.dp),
                    elevation = 0.dp
                ) {
                    Column(
                        modifier
                            .background(color = MaterialTheme.colors.background)
                    ) {

                        Box(
                            modifier = modifier
                                .padding(12.dp)
                                .fillMaxWidth(), contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painterResource(id = R.drawable.wattpad),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit, modifier = modifier
                                        .padding(top = 0.dp, bottom = 8.dp)
                                        .size(50.dp)
                                )

                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colors.iconColor,
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = maven_pro_regular
                                            )
                                        ) {
                                            append(stringResource(id = R.string.wall))
                                        }

                                        withStyle(
                                            style = SpanStyle(
                                                color = Color(0xFFfe6c40),
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = maven_pro_regular
                                            )
                                        ) {
                                            append(stringResource(id = R.string.pap))
                                        }

                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colors.iconColor,
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = maven_pro_regular
                                            )
                                        ) {
                                            append(" " + stringResource(id = R.string.pro))
                                        }
                                    }
                                )

                                Divider(
                                    modifier.padding(8.dp),
                                    thickness = 1.dp,
                                    color = MaterialTheme.colors.textColor
                                )

                                Column(
                                    modifier = modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start
                                ) {

                                    Text(
                                        buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colors.textColor,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    fontFamily = maven_pro_regular
                                                )
                                            ) {
                                                append("-")
                                            }

                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colors.textColor,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    fontFamily = maven_pro_regular
                                                )
                                            ) {
                                                append("\t\t" + stringResource(id = R.string.no_ads))
                                            }
                                        })

                                    Text(
                                        buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colors.textColor,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    fontFamily = maven_pro_regular
                                                )
                                            ) {
                                                append("-")
                                            }

                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colors.textColor,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    fontFamily = maven_pro_regular
                                                )
                                            ) {
                                                append("\t\t" + stringResource(id = R.string.unlimited_walls))
                                            }
                                        })

                                    Text(
                                        buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colors.textColor,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    fontFamily = maven_pro_regular
                                                )
                                            ) {
                                                append("-")
                                            }

                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colors.textColor,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    fontFamily = maven_pro_regular
                                                )
                                            ) {
                                                append("\t\t" + stringResource(id = R.string.extra_features))
                                            }
                                        })

                                }
                            }
                        }
                        Row(
                            modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .background(
                                    color = MaterialTheme.colors.customDialogBottomColor
                                ),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {

                            TextButton(
                                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.bottomAppBarContentColor),
                                shape = RoundedCornerShape(10.dp),
                                modifier = modifier
                                    .padding(top = 6.dp, bottom = 6.dp)
                                    .width(80.dp),
                                onClick = {
                                    dialogState.value = false
                                    val intent = Intent(Intent.ACTION_VIEW)
                                    intent.data =
                                        Uri.parse("https://play.google.com/store/apps/dev?id=7870775867932667955")
                                    try {
                                        ContextCompat.startActivity(context, intent, null)
                                    } catch (e: Exception) {
                                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                                    }
                                }) {
                                Text(
                                    text = stringResource(id = R.string.get_pro),
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
            maxHeight > 370.dp -> {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    modifier = modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
                    elevation = 0.dp
                ) {
                    Column(
                        modifier
                            .background(color = MaterialTheme.colors.background)
                    ) {

                        Box(
                            modifier = modifier
                                .padding(16.dp)
                                .fillMaxWidth(), contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painterResource(id = R.drawable.wattpad),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit, modifier = modifier
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

                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colors.iconColor,
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = maven_pro_regular
                                            )
                                        ) {
                                            append(" " + stringResource(id = R.string.pro))
                                        }
                                    }
                                )

                                Divider(
                                    modifier.padding(10.dp),
                                    thickness = 1.dp,
                                    color = MaterialTheme.colors.textColor
                                )

                                Column(
                                    modifier = modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start
                                ) {

                                    Text(
                                        buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colors.textColor,
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    fontFamily = maven_pro_regular
                                                )
                                            ) {
                                                append("-")
                                            }

                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colors.textColor,
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    fontFamily = maven_pro_regular
                                                )
                                            ) {
                                                append("\t\t" + stringResource(id = R.string.no_ads))
                                            }
                                        })

                                    Text(
                                        buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colors.textColor,
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    fontFamily = maven_pro_regular
                                                )
                                            ) {
                                                append("-")
                                            }

                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colors.textColor,
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    fontFamily = maven_pro_regular
                                                )
                                            ) {
                                                append("\t\t" + stringResource(id = R.string.unlimited_walls))
                                            }
                                        })

                                    Text(
                                        buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colors.textColor,
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    fontFamily = maven_pro_regular
                                                )
                                            ) {
                                                append("-")
                                            }

                                            withStyle(
                                                style = SpanStyle(
                                                    color = MaterialTheme.colors.textColor,
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    fontFamily = maven_pro_regular
                                                )
                                            ) {
                                                append("\t\t" + stringResource(id = R.string.extra_features))
                                            }
                                        })

                                }
                            }
                        }
                        Row(
                            modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                                .background(
                                    color = MaterialTheme.colors.customDialogBottomColor
                                ),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {

                            TextButton(
                                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.bottomAppBarContentColor),
                                shape = RoundedCornerShape(10.dp),
                                modifier = modifier
                                    .padding(top = 10.dp, bottom = 10.dp)
                                    .width(80.dp),
                                onClick = {
                                    dialogState.value = false
                                    val intent = Intent(Intent.ACTION_VIEW)
                                    intent.data =
                                        Uri.parse("https://play.google.com/store/apps/dev?id=7870775867932667955")
                                    try {
                                        ContextCompat.startActivity(context, intent, null)
                                    } catch (e: Exception) {
                                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                                    }
                                }) {
                                Text(
                                    text = stringResource(id = R.string.get_pro),
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
        }
    }
}