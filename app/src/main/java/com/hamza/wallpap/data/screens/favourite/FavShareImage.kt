package com.hamza.wallpap.data.screens.favourite

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import java.net.URL

fun favShareImage(context: Context, data: String, image: Bitmap?) {
    var image = image
    val thread = Thread {
        try {
            val url = URL(data)
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    thread.start()

    val intent = Intent(Intent.ACTION_SEND).setType("image/*")

    val path = MediaStore.Images.Media.insertImage(
        context.contentResolver,
        image,
        "ok",
        null
    )

    val uri = Uri.parse(path)
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    intent.putExtra(
        Intent.EXTRA_TEXT,
        "Download WallPap for more exciting WallPapers!"
    )
    context.startActivity(intent)
}