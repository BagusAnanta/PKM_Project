package com.bsoftware.myapplication

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bsoftware.myapplication.createfile.CreateFileFromBitmap
import com.bsoftware.myapplication.dialogalert.DatePickerCustomDialog
import com.bsoftware.myapplication.imagepreprocessing.ImagePreprocessing
import com.bsoftware.myapplication.ui.theme.MyApplicationTheme
import com.bsoftware.myapplication.viewmodel.UserReportViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReportActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ReportForm(Modifier.padding(innerPadding))
                }
            }
        }

    }
}


    @Composable
    fun ReportForm(modifier: Modifier = Modifier, userReportViewModel : UserReportViewModel = viewModel()) {
        val context : Context = LocalContext.current

        var description by remember { mutableStateOf("") }
        var reportDate by remember { mutableStateOf("") }
        var photo by remember { mutableStateOf("") }
        var photoFile by remember {mutableStateOf<File?>(null)}

        var showDialogDate by remember { mutableStateOf(false) }

        var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val filePhotoNameFormat = "IMG_$timeStamp.jpg"

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, timeStamp)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val resolver = context.contentResolver
        val imageUri : Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { captureBitmap : Bitmap? ->
            try{
                imageUri?.let {
                    resolver.openOutputStream(it).use { outputStream ->
                        // check at here
                        imageBitmap = ImagePreprocessing(context).rotateImage(captureBitmap!!, 90f)
                        photoFile = CreateFileFromBitmap().createFileFromBitmap(imageBitmap!!,imageUri.toString(), filePhotoNameFormat)
                    }
                }

            } catch (e : NullPointerException) {
                Log.e("laucherError", e.toString())
            }
        }

        Box(modifier = Modifier.fillMaxSize()){
            Image(
                painter = painterResource(id = R.drawable.background_signup_new1),
                contentDescription = "Background Login",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start),
                ) {
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
                    verticalArrangement = Arrangement.Center
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
                                launcher.launch(null)
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
                        onClick = {
                            userReportViewModel.createReportUser(
                                "Axc12fCb",
                                description,
                                reportDate,
                                photoFile!!
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.brown),
                        )
                    ) {
                        Text(
                            text = "Kirim",
                            style = TextStyle(
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }


    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun ReportPreview() {
        MyApplicationTheme {
            ReportForm()
        }
    }