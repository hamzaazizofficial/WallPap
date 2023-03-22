package com.hamza.wallpap.util

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.net.URL

@RequiresApi(Build.VERSION_CODES.N)
fun setWallPaper(context: Context, fullUrl: String, wallpaperAs: Int) {

    val metrics = DisplayMetrics()
    val windowsManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowsManager.defaultDisplay.getMetrics(metrics)

    val screenWidth = metrics.widthPixels
    val screenHeight = metrics.heightPixels.minus(300)

    val wallpaperManager = WallpaperManager.getInstance(context)
    wallpaperManager.suggestDesiredDimensions(screenWidth, screenHeight)

    val width = wallpaperManager.desiredMinimumWidth
    val height = wallpaperManager.desiredMinimumHeight
    Toast.makeText(context, "Setting your Wallpaper...", Toast.LENGTH_LONG).show()

    val thread = Thread {
        try {
            val url = URL(fullUrl)
            val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            val wallpaper = Bitmap.createScaledBitmap(image, width, height, true)
            when (wallpaperAs) {
                1 -> wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_SYSTEM)
                2 -> wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_LOCK)
                3 -> {
                    wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_LOCK)
                    wallpaperManager.setBitmap(wallpaper, null, true, WallpaperManager.FLAG_SYSTEM)
                }
            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    thread.start()
}