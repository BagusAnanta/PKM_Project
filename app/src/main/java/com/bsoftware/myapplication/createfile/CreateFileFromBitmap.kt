package com.bsoftware.myapplication.createfile

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class CreateFileFromBitmap {

    fun testCreateFileFromBitmap(context : Context, bitmap: Bitmap, filePhotoName : String) : File? {
        val pictureDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        if(pictureDir == null){
            Log.e("CreateFileFromBitmap", "External Storage Is Not Available")
            return null
        }

        if(!pictureDir.exists()){
            if(!pictureDir.mkdirs()){
                Log.e("CreateFileFromBitmap", "Failed To Create Directory")
                return null
            }
        }

        val imageFile = File(pictureDir, filePhotoName)
        try{
            val compressBitmap = compressImageIntoTargetSize(bitmap, 500 * 1024)

            FileOutputStream(imageFile).use {fos ->
                compressBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            }

            Log.d("CreateFileFromBitmap", "Image File Path: ${imageFile.absolutePath}")
            return imageFile

        } catch (e:Exception){
            e.printStackTrace()
            return null
        }

    }

    private fun compressImageIntoTargetSize(bitmap : Bitmap, targetSize : Int) : Bitmap{
        var quality = 100
        var streamLength : Int
        var compressBitmap = bitmap

        do {
            val byteArray = ByteArrayOutputStream()
            compressBitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArray)
            streamLength = byteArray.toByteArray().size
            byteArray.close()

            if(streamLength > targetSize){
                quality -= 5

                if(quality < 5){
                    break
                }
            } else {
                break
            }
        } while (streamLength > targetSize)

        Log.d("Image Size Compress Image (Original Size)", "Original Size: ${bitmap.byteCount / 1024} KB")
        Log.d("Image Size Compress Image (Compress Size)", "Compressed Size: ${streamLength / 1024} KB")

        return compressBitmap
    }

   /* private fun saveImageToExternalDirectory(context : Context, bitmap : Bitmap, fileName : String) : Uri? {
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
    }*/
}