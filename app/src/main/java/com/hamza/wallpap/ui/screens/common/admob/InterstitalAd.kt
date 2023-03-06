package com.hamza.wallpap.ui.screens.common.admob

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

@Composable
fun MainInterstitialAd(){
    val context: Context = LocalContext.current
    var interstitialAd: InterstitialAd?
    val adRequest = AdRequest.Builder().build()

    InterstitialAd.load(
        context,
        "ca-app-pub-3940256099942544/1033173712",
        adRequest,
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                interstitialAd = null
            }

            override fun onAdLoaded(p0: InterstitialAd) {
                interstitialAd = p0
                interstitialAd?.show(context as Activity)
            }
        })
}