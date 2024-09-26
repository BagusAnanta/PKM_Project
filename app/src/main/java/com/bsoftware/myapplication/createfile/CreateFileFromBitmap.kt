package com.bsoftware.myapplication.createfile

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toFile
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateFileFromBitmap {

    private fun saveImageToCache(context : Context, bitmap : Bitmap, fileName : String) : Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            val outputStream = context.contentResolver.openOutputStream(it)
            outputStream?.use { outputstream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputstream)
            }
        }

        return uri
    }

    // this function for create file from bitmap, and this function use for create file in internal storage in application
    fun createFileFromBitmap(context : Context, bitmap: Bitmap, filePhotoName : String) : File? {

        val imagePath = saveImageToCache(context,bitmap,filePhotoName)?.path
        Log.d("TAG", "createFileFromBitmap: $imagePath")

        // val file = File(imagePath,filePhotoName)

        return try{
            // reduce the size of file
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(imagePath,options)

            options.inSampleSize = calculateSampleSize(options,800,600)
            options.inJustDecodeBounds = false

            val compressBitmap = BitmapFactory.decodeFile(imagePath,options)
            val compressIntoImageFile = File(imagePath,filePhotoName)

            FileOutputStream(compressIntoImageFile).use {
                compressBitmap.compress(Bitmap.CompressFormat.JPEG,100,it)
            }

            compressIntoImageFile

        } catch (e:Exception){
            e.printStackTrace()
            null
        }
    }

    private fun calculateSampleSize(options : BitmapFactory.Options, reqWidth : Int, reqHeight : Int) : Int {
        val (height : Int, width : Int) = options.outHeight to options.outWidth
        var inSampleSize = 1

        if(height > reqHeight || width > reqWidth){
            val halfHeight : Int = height / 2
            val halfWidth : Int = width / 2

            while(halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth){
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }


}