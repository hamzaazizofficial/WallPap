package com.hamza.wallpap.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

suspend fun getBitmapFromUrl(url: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val inputStream = URL(url).openStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

suspend fun loadReducedSizeBitmapFromUrl(urlString: String): Bitmap? = withContext(Dispatchers.IO) {
    var bitmap: Bitmap? = null
    var input: InputStream? = null

    try {
        val url = URL(urlString)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()

        input = connection.inputStream

        // Decode the bitmap with inJustDecodeBounds = true to check the dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(input, null, options)

        // Calculate the sample size based on the requested width and height
        options.inSampleSize = calculateInSampleSize(options, 2000, 2000)

        // Decode the bitmap with the calculated sample size
        options.inJustDecodeBounds = false
        input.close()
        input = url.openConnection().getInputStream()
        bitmap = BitmapFactory.decodeStream(input, null, options)

    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        input?.close()
    }

    bitmap
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight = height / 2
        val halfWidth = width / 2

        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}