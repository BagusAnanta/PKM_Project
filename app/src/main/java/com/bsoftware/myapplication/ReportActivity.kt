package com.bsoftware.myapplication

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bsoftware.myapplication.dialogalert.DatePickerCustomDialog
import com.bsoftware.myapplication.imagepreprocessing.ImagePreprocessing
import com.bsoftware.myapplication.ui.theme.MyApplicationTheme

class ReportActivity : ComponentActivity() {

    var image_uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // check camera permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
            ) {
                val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, 112)
            }
        }

        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ReportForm(Modifier.padding(innerPadding))
                }
            }
        }

    }


    @Composable
    fun ReportForm(modifier: Modifier = Modifier) {
        var description by remember { mutableStateOf("") }
        var reportDate by remember { mutableStateOf("") }
        var photo by remember { mutableStateOf("") }

        /* val googleFontIcon = FontFamily(
        Font(
            googleFont = GoogleFosnt("Material Icons"),

        )
    )*/

        var showDialogDate by remember { mutableStateOf(false) }

        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Column {
                Text(
                    text = "Formulir",
                    fontSize = 20.sp,
                )
                Text(
                    text = "Laporan Kejadian",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = {
                        Text("Deskripsi")
                    },
                    maxLines = 4,
                    modifier = Modifier
                        .height(200.dp)
                        .width(350.dp),
                    shape = RoundedCornerShape(20.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Date Picker
                OutlinedTextField(
                    value = reportDate,
                    onValueChange = { reportDate = it },
                    readOnly = true,
                    modifier = Modifier
                        .clickable { showDialogDate = true }
                        .fillMaxWidth()
                        .width(350.dp),
                    label = {
                        Text("Tanggal Laporan")
                    },
                    shape = RoundedCornerShape(20.dp),
                    enabled = false,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Tanggal Kejadian"
                        )
                    }
                )

                if (showDialogDate) {
                    DatePickerCustomDialog(
                        onDateSelected = { reportDate = it },
                        onDismiss = { showDialogDate = false })
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = photo,
                    onValueChange = { photo = it },
                    readOnly = true,
                    modifier = Modifier
                        .clickable {
                            // clickable for photo
                            openCamera()
                        }
                        .fillMaxWidth()
                        .width(350.dp),
                    label = {
                        Text("Foto Kejadian (Opsional)")
                    },
                    shape = RoundedCornerShape(20.dp),
                    enabled = false,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Foto Kejadian"
                        )
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "Kirim")
                }
            }
        }
    }

    private fun openCamera() {
        val values: ContentValues = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camere")

        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)

        cameraActivityResultLaucher.launch(cameraIntent)
    }

    private val cameraActivityResultLaucher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val bitmapConverter = ImagePreprocessing(this).convertImageIntoBitmap(image_uri!!)
                // sent image at here
            }
        }


    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun ReportPreview() {
        MyApplicationTheme {
            ReportForm()
        }
    }
}