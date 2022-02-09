package com.example.phonebook

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import java.io.InputStream
import kotlin.math.abs

class BitmapCropper {
    companion object {
        private val minSize = 200
        private val scaleSize = 350
        fun createBitmap(context: Context?, uri: Uri): Bitmap? {
            val inputStream = context?.contentResolver?.openInputStream(uri)
            val src = BitmapFactory.decodeStream(inputStream)
            if (src.height < minSize && src.width < minSize)
                Toast.makeText(context, "Image is too small, try selecting a different image", Toast.LENGTH_SHORT).show()
            else {
                val min = if (src.height > src.width) src.width else src.height
                // Crops the image
                val cropImg = Bitmap.createBitmap(src, 0, 0, min, min)
                // Scales the image
                return Bitmap.createScaledBitmap(cropImg, scaleSize, scaleSize, false)
            }
            return null
        }
    }
}