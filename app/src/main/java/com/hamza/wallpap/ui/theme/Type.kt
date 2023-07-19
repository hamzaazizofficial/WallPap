package com.hamza.wallpap.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.hamza.wallpap.R

// Set of Material typography styles to start with

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val defaultFont = GoogleFont("Maven Pro")

val defaultFontFamily = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = defaultFont,
        fontProvider = provider
    )
)

val monoton_regularFont = GoogleFont("Monoton")
val abeezeeFont = GoogleFont("ABeeZee")
val allertaFont = GoogleFont("Allerta")
val amaranthFont = GoogleFont("Amaranth")
val amatic_scFont = GoogleFont("Amatic SC")
val archivo_blackFont = GoogleFont("Archivo Black")
val arimoFont = GoogleFont("Arimo")
val bungee_inlineFont = GoogleFont("Bungee Inline")
val cabinFont = GoogleFont("Cabin")
val chivoFont = GoogleFont("Chivo")
val comicFont = GoogleFont("Comic Neue")
val faster_oneFont = GoogleFont("Faster One")
val francois_oneFont = GoogleFont("Francois One")
val montserratFont = GoogleFont("Montserrat")
val nunitoFont = GoogleFont("Nunito")
val oxygenFont = GoogleFont("Oxygen")
val permanent_markerFont = GoogleFont("Permanent Marker")
//val press_start_2pFont = GoogleFont("Press Start 2P")
val robotoFont = GoogleFont("Roboto")
val schoolbellFont = GoogleFont("Schoolbell")

val monoton_regular = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = monoton_regularFont,
        fontProvider = provider
    )
)

val maven_pro_regular = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = defaultFont,
        fontProvider = provider
    )
)

val abeezee = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = abeezeeFont,
        fontProvider = provider
    )
)

val allerta = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = allertaFont,
        fontProvider = provider
    )
)

val amaranth = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = amaranthFont,
        fontProvider = provider
    )
)

val amatic_sc = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = amatic_scFont,
        fontProvider = provider
    )
)

val archivo_black = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = archivo_blackFont,
        fontProvider = provider
    )
)

val arimo = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = arimoFont,
        fontProvider = provider
    )
)

val bungee_inline = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = bungee_inlineFont,
        fontProvider = provider
    )
)

val cabin = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = cabinFont,
        fontProvider = provider
    )
)

val chivo = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = chivoFont,
        fontProvider = provider
    )
)

val comic = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = comicFont,
        fontProvider = provider
    )
)

val faster_one = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = faster_oneFont,
        fontProvider = provider
    )
)

val francois_one = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = francois_oneFont,
        fontProvider = provider
    )
)

val montserrat = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = montserratFont,
        fontProvider = provider
    )
)

val nunito = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = nunitoFont,
        fontProvider = provider
    )
)

val oxygen = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = oxygenFont,
        fontProvider = provider
    )
)

val permanent_marker = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = permanent_markerFont,
        fontProvider = provider
    )
)

val roboto = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = robotoFont,
        fontProvider = provider
    )
)

val schoolbell = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(
        googleFont = schoolbellFont,
        fontProvider = provider
    )
)

//val arimo_bold = FontFamily(
//    Font(R.font.arimo_bold)
//)
//
//val arimo_italic = FontFamily(
//    Font(R.font.arimo_italic)
//)

//val bahnschrift = FontFamily(
//    Font(R.font.bahnschrift)
//)

//val comicbd = FontFamily(
//    Font(R.font.comicbd)
//)

//val consola = FontFamily(
//    Font(R.font.consola)
//)
//
//val consolab = FontFamily(
//    Font(R.font.consolab)
//)

//val cour = FontFamily(
//    Font(R.font.cour)
//)

//val courbd = FontFamily(
//    Font(R.font.courbd)
//)

//val impact = FontFamily(
//    Font(R.font.impact)
//)

//val lucon = FontFamily(
//    Font(R.font.lucon)
//)


//val nunito_bold = FontFamily(
//    Font(R.font.nunito_bold)
//)
//
//val nunito_light = FontFamily(
//    Font(R.font.nunito_light)
//)
//
//val nunito_semibold = FontFamily(
//    Font(R.font.nunito_semibold)
//)







//val press_start_2p = FontFamily(
//    androidx.compose.ui.text.googlefonts.Font(
//        googleFont = press_start_2pFont,
//        fontProvider = provider
//    )
//)



//val seguibl = FontFamily(
//    Font(R.font.seguibl)
//)
//
//val trebuc = FontFamily(
//    Font(R.font.trebuc)
//)
//
//val trebuc_bd = FontFamily(
//    Font(R.font.trebucbd)
//)
//
//val verdana = FontFamily(
//    Font(R.font.verdana)
//)

//@OptIn(ExperimentalTextApi::class)
//val AppFontTypography = Typography(
//    defaultFontFamily = getGoogleFontFamily(
//        name = "Poppins",
//        weights = listOf(
//            FontWeight.Normal,
//            FontWeight.Bold,
//            FontWeight.ExtraLight,
//            FontWeight.SemiBold
//        )
//    )
//)

//@OptIn(ExperimentalTextApi::class)
//private fun getGoogleFontFamily(
//    name: String,
//    provider: GoogleFont.Provider = googleFontProvider,
//    weights: List<FontWeight>
//): FontFamily {
//    return FontFamily(
//        weights.map {
//            Font(GoogleFont(name), provider, it)
//        }
//    )
//}
//
//@OptIn(ExperimentalTextApi::class)
//private val googleFontProvider: GoogleFont.Provider by lazy {
//    GoogleFont.Provider(
//        providerAuthority = "com.google.android.gms.fonts",
//        providerPackage = "com.google.android.gms",
//        certificates = R.array.com_google_android_gms_fonts_certs
//    )
//}


val Typography = Typography(
    body1 = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    defaultFontFamily = defaultFontFamily
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)