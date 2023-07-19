package com.hamza.wallpap

import android.app.Application
import android.util.Log
import com.applovin.sdk.AppLovinSdk
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        AppLovinSdk.getInstance(applicationContext).mediationProvider = "max"
        AppLovinSdk.getInstance(applicationContext)
            .initializeSdk {
                // AppLovin SDK is initialized, start loading ads
//                    createInterstitialAd(this, wallpaperFullScreenViewModel)
                Log.d("ad", "initialized")
            }
    }
}