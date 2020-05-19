package com.himorfosis.kelolabelanja.service

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.squareup.picasso.Transformation
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ImageService {

    companion object {

        var STORAGE_PERMISSION_CODE = 123
        val GALLERY = 1
        val CAMERA = 2

        fun createImageFileJpg(bitmap: Bitmap): File? {
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), System.currentTimeMillis().toString() + "-profile.jpg")
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            val bitmapdata = bos.toByteArray()
            //write the bytes in file
            try {
                val fos = FileOutputStream(file)
                fos.write(bitmapdata)
                fos.flush()
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return file
        }

        fun getPath(context: Context, uri: Uri): String? {
            val projection = arrayOf(MediaStore.MediaColumns.DATA)
            val cursor = context.contentResolver.query(uri, projection, null, null, null)
            return if (cursor != null) {
                cursor.moveToFirst()
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                val filePath = cursor.getString(columnIndex)
                cursor.close()
                filePath
            } else uri.path
        }

        fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
            val matrix = Matrix()
            matrix.postRotate(angle)
            return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
        }

        // resize image
        fun loadResizeImageFromGallery(context: Context, uri: Uri?): Bitmap? {

            var bitmap: Bitmap? = null

            val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME)
            val cursor = context.contentResolver.query(uri!!, projection, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()

                val columnIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME)
                if (columnIndex != -1) {
                    Thread(Runnable {
                        try {
                            val dataBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                            val file = (dataBitmap.height * (512.0 / dataBitmap.width)).toInt()
                            bitmap = Bitmap.createScaledBitmap(dataBitmap, 512, file, true)
                            // THIS IS THE BITMAP IMAGE WE ARE LOOKING FOR
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }
                    }).start()
                }
            }
            cursor!!.close()

            return bitmap
        }


        fun isWriteStoragePermissionGranted(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= 23) {
                if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    true
                } else {
                    ActivityCompat.requestPermissions((context as Activity), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
                    false
                }
            } else {
                //permission is automatically granted on sdk<23 upon installation
                isLog("Permission is granted")
                true
            }
        }

        fun isReadStoragePermissionGranted(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= 23) {
                if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((context as Activity), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
                    true
                } else {
                    ActivityCompat.requestPermissions((context as Activity), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
                    false
                }
            } else { //permission is automatically granted on sdk<23 upon installation
                ActivityCompat.requestPermissions((context as Activity), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
                true
            }
        }


        fun requestStoragePermission(context: Context?) {
            if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) if (ActivityCompat.shouldShowRequestPermissionRationale((context as Activity?)!!, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            }
            //And finally ask for the permission
            ActivityCompat.requestPermissions((context as Activity?)!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
        }

        private fun isLog(msg: String) {
            Log.e("ImageService", msg)
        }

    }

}