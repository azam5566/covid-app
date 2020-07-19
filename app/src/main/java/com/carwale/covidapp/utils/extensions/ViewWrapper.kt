package com.carwale.covidapp.utils.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.view.View


fun View.toBitmap(): Bitmap {
    val returnedBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(returnedBitmap)
    this.background?.draw(canvas)
    this.draw(canvas)
    return returnedBitmap
}

fun View.onClickOpenBrowser(ctx: Context, url: String) {
    setOnClickListener {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)

        //pass the url to intent data
        intent.data = Uri.parse(url)
        ctx.startActivity(intent)
    }
}