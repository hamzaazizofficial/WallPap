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
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.paging.ExperimentalPagingApi
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkConfiguration
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.hamza.wallpap.ui.MainScreen
import com.hamza.wallpap.ui.animation.CircularReveal
import com.hamza.wallpap.ui.theme.WallPapTheme
import com.hamza.wallpap.util.ThemeSetting
import com.hamza.wallpap.util.ThemeSettingPreference
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

lateinit var interstitialAd: MaxInterstitialAd

@AndroidEntryPoint
class MainActivity : ComponentActivity(), MaxAdListener {
    private lateinit var themeSetting: ThemeSetting
    private var retryAttempt = 0.0

    private fun createInterstitialAd() {
        interstitialAd = MaxInterstitialAd("f529325940f663df", this)
        interstitialAd.setListener(this)

        // Load the first ad
        interstitialAd.loadAd()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @OptIn(
        ExperimentalPagingApi::class, ExperimentalComposeUiApi::class,
        ExperimentalAnimationApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppLovinSdk.getInstance(applicationContext).mediationProvider = "max"
            AppLovinSdk.getInstance(applicationContext)
                .initializeSdk { configuration: AppLovinSdkConfiguration ->
                    // AppLovin SDK is initialized, start loading ads
                    createInterstitialAd()
                    Log.d("ad", "initialized")
//                if  ( interstitialAd.isReady )
//                {
//                    interstitialAd.showAd()
//                }
                }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
//                val splashScreen = installSplashScreen()
//                splashScreen.setKeepOnScreenCondition { true }
//            }
            themeSetting = ThemeSettingPreference(context = LocalContext.current)
            val theme = themeSetting.themeStream.collectAsState()
            val useDarkColors = when (theme.value) {
//                com.hamza.wallpap.util.WallPapTheme.MODE_AUTO -> isSystemInDarkTheme()
                com.hamza.wallpap.util.WallPapTheme.MODE_DAY -> false
                com.hamza.wallpap.util.WallPapTheme.MODE_NIGHT -> true
            }

            val isSystemDark = isSystemInDarkTheme()
            var darkTheme by remember { mutableStateOf(isSystemDark) }
            val onThemeToggle = { darkTheme = !darkTheme }
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
                            navController = navController,
                            onItemSelected = { theme -> themeSetting.theme = theme })
                    }
                }
            }
        }
    }

    override fun onAdLoaded(maxAd: MaxAd) {
        // Interstitial ad is ready to be shown. interstitialAd.isReady() will now return 'true'

        // Reset retry attempt
        retryAttempt = 0.0
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

        retryAttempt++
        val delayMillis =
            TimeUnit.SECONDS.toMillis(Math.pow(2.0, Math.min(6.0, retryAttempt)).toLong())

        Handler().postDelayed({ interstitialAd.loadAd() }, delayMillis)
    }

    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {
        // Interstitial ad failed to display. AppLovin recommends that you load the next ad.
        interstitialAd.loadAd()
    }
}
