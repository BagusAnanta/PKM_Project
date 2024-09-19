package com.bsoftware.myapplication.createfile

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateFileFromBitmap {

    // this function for create file from bitmap, and this function use for creta file in internal storage in application
    fun createFileFromBitmap(bitmap: Bitmap,directoryLocation : String, filePhotoName : String) : File? {

        val file = File(directoryLocation,filePhotoName)

        return try{
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val bitmapData = byteArrayOutputStream.toByteArray()

            val fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(bitmapData)
            fileOutputStream.flush()
            fileOutputStream.close()

            file
        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }
}