package com.bsoftware.myapplication.imagepreprocessing

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import java.io.IOException

class ImagePreprocessing(private val context : Context) {

    fun convertImageIntoBitmap(imageUri : Uri) : Bitmap {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(imageUri)
        val originalBitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        // rotate in here
        var rotateDegress = 0

        try{
            val exif = ExifInterface(contentResolver.openInputStream(imageUri)!!)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL)

            rotateDegress = when(orientation){
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
        } catch (e : IOException){
            e.printStackTrace()
        }

        return rotateImage(originalBitmap,rotateDegress.toFloat())
    }

    fun rotateImage(image : Bitmap, degress : Float) : Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degress)

        return Bitmap.createBitmap(image,0,0,image.width,image.height,matrix,true)
    }
}