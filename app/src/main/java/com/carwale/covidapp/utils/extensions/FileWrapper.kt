package com.carwale.covidapp.utils.extensions

import android.content.res.Resources
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*

fun File.toMultipart(key: String): MultipartBody.Part = MultipartBody.Part.createFormData(
    key,
    this.name,
    RequestBody.create(MediaType.parse("multipart/form-data"), this)
)

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun String.isValidEmail() =
    this.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isWebAddress() =
    this.isNotEmpty() && android.util.Patterns.WEB_URL.matcher(this).matches()
