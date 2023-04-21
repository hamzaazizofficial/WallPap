package com.hamza.wallpap.ui.screens.editor

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.hamza.wallpap.model.CustomWallpaperBackgroundColor
import com.hamza.wallpap.ui.theme.*
import kotlin.random.Random

class CustomWallpaperViewModel : ViewModel() {

    var saturationSliderValue = mutableStateOf(1f)
    var saturationSliderPosition = mutableStateOf(1f)

    var editorDropDownExpanded = mutableStateOf(false)
    var imageRotate = mutableStateOf(0f)
    var contentScale = mutableStateOf(ContentScale.Crop)
    var redColorSliderPosition = mutableStateOf(0f)
    var blueColorSliderPosition =  mutableStateOf(0f)
    var greenColorSliderPosition =   mutableStateOf(0f)
    var alphaColorSliderPosition =  mutableStateOf(255f)
    var redColorValue =  mutableStateOf(0)
    var blueColorValue = mutableStateOf(0)
    var greenColorValue = mutableStateOf(0)
    var alphaColorValue = mutableStateOf(255)

    val random = Random(System.currentTimeMillis())
    var fontFamilyName = mutableStateOf("Roboto")
    val fontFamilyItems =
        arrayListOf(
            FontFamilySearchChip("Roboto", roboto),
            FontFamilySearchChip("Maven Pro", maven_pro_regular),
            FontFamilySearchChip("Monoton", monoton_regular),
            FontFamilySearchChip("Normal", FontFamily.Default),
            FontFamilySearchChip("Monospace", FontFamily.Monospace),
            FontFamilySearchChip("Serif", FontFamily.Serif),
            FontFamilySearchChip("Sans Serif", FontFamily.SansSerif),
            FontFamilySearchChip("Abeezee", abeezee),
            FontFamilySearchChip("Aileron Bold", aileron_bold),
            FontFamilySearchChip("Aileron Regular", aileron_regular),
            FontFamilySearchChip("Aileron SemiBold", aileron_semibold),
            FontFamilySearchChip("Allerta", allerta),
            FontFamilySearchChip("Allerta Stencil", allerta_stencil),
            FontFamilySearchChip("Amaranth", amaranth),
            FontFamilySearchChip("Amaranth SC", amatic_sc),
            FontFamilySearchChip("Amatic SC", amatic_sc),
            FontFamilySearchChip("Amatic Bold", amatic_sc_boldf),
            FontFamilySearchChip("Archivo Black", archivo_black),
            FontFamilySearchChip("Ariblk", ariblk),
            FontFamilySearchChip("Arimo", arimo),
            FontFamilySearchChip("Arimo Bold", arimo_bold),
            FontFamilySearchChip("Arimo Italic", arimo_italic),
            FontFamilySearchChip("Bahnschrift", bahnschrift),
            FontFamilySearchChip("Bungee", bungee_inline),
            FontFamilySearchChip("Cabin", cabin),
            FontFamilySearchChip("Chivo", chivo),
            FontFamilySearchChip("Comic", comic),
            FontFamilySearchChip("Comic Bold", comicbd),
            FontFamilySearchChip("Consola", consola),
            FontFamilySearchChip("Consolab", consolab),
            FontFamilySearchChip("Cour", cour),
            FontFamilySearchChip("Cour Bold", courbd),
            FontFamilySearchChip("Faster One", faster_one),
            FontFamilySearchChip("Francois One", francois_one),
            FontFamilySearchChip("Impact", impact),
            FontFamilySearchChip("Lucon", lucon),
            FontFamilySearchChip("Montserrat", montserrat),
            FontFamilySearchChip("Nunito", nunito),
            FontFamilySearchChip("Nunito Bold", nunito_bold),
            FontFamilySearchChip("Nunito Light", nunito_light),
            FontFamilySearchChip("Nunito SemiBold", nunito_semibold),
            FontFamilySearchChip("Oxygen", oxygen),
            FontFamilySearchChip("Permanent Marker", permanent_marker),
            FontFamilySearchChip("Press Start", press_start_2p),
            FontFamilySearchChip("School Bell", schoolbell),
            FontFamilySearchChip("Seguibl", seguibl),
            FontFamilySearchChip("Trebuc", trebuc),
            FontFamilySearchChip("Trebuc Bold", trebuc_bd),
            FontFamilySearchChip("Verdana", verdana)
        )
    var selectedFontFamilyIndex = mutableStateOf(0)

    val colorItems = arrayListOf(
        CustomWallpaperBackgroundColor(Color(0xFFFFFFFF), 8),
        CustomWallpaperBackgroundColor(Color(0xFF000000), 8),
        CustomWallpaperBackgroundColor(Color(0xFF41FDF7), 8),
        CustomWallpaperBackgroundColor(Color(0xFFFF5722), 8),
        CustomWallpaperBackgroundColor(Color(0xFFE91E63), 8),
        CustomWallpaperBackgroundColor(Color(0xFF8BC34A), 8),
        CustomWallpaperBackgroundColor(Color(0xFF3F51B5), 8),
        CustomWallpaperBackgroundColor(Color(0xFF9C27B0), 8),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
        CustomWallpaperBackgroundColor(
            Color(
                random.nextFloat(),
                random.nextFloat(),
                random.nextFloat()
            ), 8
        ),
    )

    var selectedTextColorIndex = mutableStateOf(1)
    var selectedBgColorIndex = mutableStateOf(50)
    var colorItem = mutableStateOf(Color(0xFFFFFFFF))

    var clearEditorDialogState = mutableStateOf(false)
    var colorPickerDialogState = mutableStateOf(false)
    var textFormatColorPickerState = mutableStateOf(false)

    var shareWallpaperVisible = mutableStateOf(false)

    var savedImageBitmap = mutableStateOf<Bitmap?>(null)

    var bgImageUri = mutableStateOf<Uri?>(null)


    var bgBoxColor = mutableStateOf(Color(0xF1FFFFFF))
    var wallpaperText = mutableStateOf("")
    var wallpaperTextSize = mutableStateOf(24.sp)
    var wallpaperTextColor = mutableStateOf(Color(0xFF000000))
    var wallpaperTextAlign = mutableStateOf(TextAlign.Center)
    var wallpaperDialogState = mutableStateOf(false)
    var wallpaperTextFontWeight = mutableStateOf(FontWeight.Normal)
    var wallpaperTextDecoration = mutableStateOf(TextDecoration.None)
    var wallpaperTextFontStyle = mutableStateOf(FontStyle.Normal)
    var textSliderPosition = mutableStateOf(24f)

    var imageTransparencySliderPosition = mutableStateOf(1f)
    var colorTransparencySliderPosition = mutableStateOf(0f)

    /* Text Format Icons Checked */
    var textAlignCenterChecked = mutableStateOf(false)
    var textAlignRightChecked = mutableStateOf(false)
    var textAlignLeftChecked = mutableStateOf(false)
    var textAlignJustifyChecked = mutableStateOf(false)
    var textFontBoldChecked = mutableStateOf(false)
    var textFontItalicChecked = mutableStateOf(false)
    var textFontStrikethroughChecked = mutableStateOf(false)
    var textFontFamily = mutableStateOf(roboto)

    var bgColorBottomSheet = mutableStateOf(false)
    var textBottomSheet = mutableStateOf(false)
    var bgImageBottomSheet = mutableStateOf(false)
    var bgImageFullUrl = mutableStateOf<String?>(null)
    var bgImageRegularUrl = mutableStateOf<String?>(null)
    var selectBgImageState = mutableStateOf(false)
    var bgImageTransparency = mutableStateOf(1f)
}