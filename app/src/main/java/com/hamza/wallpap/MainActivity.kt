package com.hamza.wallpap

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.hamza.wallpap.ui.MainScreen
import com.hamza.wallpap.ui.MainViewModel
import com.hamza.wallpap.ui.animation.CircularReveal
import com.hamza.wallpap.ui.screens.wallpaper.WallpaperFullScreenViewModel
import com.hamza.wallpap.ui.theme.WallPapTheme
import com.hamza.wallpap.util.ThemeSetting
import com.hamza.wallpap.util.ThemeSettingPreference
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import kotlin.math.pow

lateinit var interstitialAd: MaxInterstitialAd
var retryAttempt = 0.0

fun createInterstitialAd(
    mainActivity: MainActivity,
    wallpaperFullScreenViewModel: WallpaperFullScreenViewModel,
) {
    interstitialAd = MaxInterstitialAd("f529325940f663df", mainActivity)
    interstitialAd.setListener(mainActivity)

    if (wallpaperFullScreenViewModel.interstitialState.value % 2 == 0) {
        interstitialAd.loadAd()

        // You can call Admob Interstitial Ad from here also.
        /* showAdmobInterstitialAd(context) */
    }
}

fun showAdmobInterstitialAd(mainActivity: MainActivity) {
    InterstitialAd.load(
        mainActivity,
        "ca-app-pub-3940256099942544/1033173712", //Change this with your own AdUnitID!
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                interstitialAd.show(mainActivity)
            }
        }
    )
}

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : ComponentActivity(), MaxAdListener {
    private lateinit var themeSetting: ThemeSetting

    @RequiresApi(Build.VERSION_CODES.N)
    @OptIn(
        ExperimentalPagingApi::class, ExperimentalComposeUiApi::class,
        ExperimentalAnimationApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf("ABCDEF012345")).build()
        )
        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        installSplashScreen().apply {
            setKeepVisibleCondition {
                mainViewModel.isLoading.value
            }
        }
        setContent {
            val wallpaperFullScreenViewModel =
                ViewModelProvider(this)[WallpaperFullScreenViewModel::class.java]

            // showing Admob Interstitial Ad (You can call this function from anywhere)
            showAdmobInterstitialAd(this)

            themeSetting = ThemeSettingPreference(context = LocalContext.current)
            val theme = themeSetting.themeStream.collectAsState()
            val useDarkColors = when (theme.value) {
//                com.hamza.wallpap.util.WallPapTheme.MODE_AUTO -> isSystemInDarkTheme()
                com.hamza.wallpap.util.WallPapTheme.MODE_DAY -> false
                com.hamza.wallpap.util.WallPapTheme.MODE_NIGHT -> true
            }

            val navController = rememberAnimatedNavController()
            CircularReveal(
                targetState = useDarkColors,
                animationSpec = tween(1600, easing = LinearOutSlowInEasing)
            ) { isDark ->
                WallPapTheme(darkTheme = isDark) {
                    Surface(
                        modifier = Modifier.semantics { testTagsAsResourceId = true },
                        color = MaterialTheme.colors.background
                    ) {
                        MainScreen(
                            wallpaperFullScreenViewModel,
                            navController = navController,
                            onItemSelected = { theme -> themeSetting.theme = theme }
                        )
                    }
                }
            }
        }
    }

    override fun onAdLoaded(maxAd: MaxAd) {
        // Interstitial ad is ready to be shown. interstitialAd.isReady() will now return 'true'
        // Reset retry attempt
        retryAttempt = 0.0
        interstitialAd.loadAd()
        interstitialAd.showAd()
    }

    override fun onAdDisplayed(p0: MaxAd?) {
        TODO("Not yet implemented")
    }

    override fun onAdHidden(p0: MaxAd?) {
        TODO("Not yet implemented")
    }

    override fun onAdClicked(p0: MaxAd?) {
        TODO("Not yet implemented")
    }

    override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {
        // Interstitial ad failed to load
        // AppLovin recommends that you retry with exponentially higher delays up to a maximum delay (in this case 64 seconds)
        Log.d("er", error.toString())
        retryAttempt++
        val delayMillis =
            TimeUnit.SECONDS.toMillis(2.0.pow(6.0.coerceAtMost(retryAttempt)).toLong())

        Handler().postDelayed({ interstitialAd.loadAd() }, delayMillis)
    }

    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
        // Interstitial ad failed to display. AppLovin recommends that you load the next ad.
        interstitialAd.loadAd()
    }
}
