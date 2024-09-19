package com.bsoftware.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.bsoftware.myapplication.dataclass.CreateUserDataClass
import com.bsoftware.myapplication.ui.theme.MyApplicationTheme

class AdminActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AdminView(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AdminView(modifier : Modifier = Modifier) {
    var nameAdmin by remember{ mutableStateOf("Bagus") }
    var colorCardforPengguna by remember{ mutableStateOf(Color.White) }
    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)

    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.background_adminmenu),
            contentDescription = "Background Admin",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )

        Column( modifier = Modifier.padding(10.dp)){
            Column{
                Text(
                    text = "Selamat Datang,",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraLight
                    ),
                    modifier = Modifier.padding(top = 10.dp)
                )

                Text(
                    text = nameAdmin,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.padding(top = 30.dp))

            Text(
                text = "Pengguna",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.padding(top = 10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.brown_calm)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                ){
                    LazyRow{
                        item{
                            Card(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .width(120.dp)
                                    .fillMaxSize()
                                    .clickable {
                                        val intent = Intent(context, VeryficationActivity::class.java)
                                        activity?.startActivity(intent)
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = colorCardforPengguna
                                )
                            ){
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Done,
                                        contentDescription = "verified icon",
                                        tint = Color.Black
                                    )

                                    Spacer(modifier = Modifier.padding(top = 10.dp))

                                    Text(
                                        text = "Verifikasi",
                                        style = TextStyle(
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }

                            Card(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .width(120.dp)
                                    .fillMaxSize()
                                    .clickable {
                                        val intent = Intent(context, AdminAccountActivity::class.java)
                                        activity?.startActivity(intent)
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = colorCardforPengguna
                                )
                            ){
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "admin icon",
                                        tint = Color.Black
                                    )

                                    Spacer(modifier = Modifier.padding(top = 10.dp))

                                    Text(
                                        text = "Akun Admin",
                                        style = TextStyle(
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }

                            Card(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .width(120.dp)
                                    .fillMaxSize()
                                    .clickable {
                                         val intent = Intent(context, UserAccountActivity::class.java)
                                         activity?.startActivity(intent)
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = colorCardforPengguna
                                )
                            ){
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = "user icon",
                                        tint = Color.Black
                                    )

                                    Spacer(modifier = Modifier.padding(top = 10.dp))

                                    Text(
                                        text = "Akun User",
                                        style = TextStyle(
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }

                        }
                    }
                }
            }

            Spacer(modifier = Modifier.padding(top = 20.dp))

            Text(
                text = "Laporan",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.padding(top = 10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.brown_calm)
                )

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                ){
                    LazyRow{
                        item{
                            Card(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .width(120.dp)
                                    .fillMaxSize()
                                    .clickable {
                                        val intent = Intent(context, DangerActivity::class.java)
                                        activity?.startActivity(intent)
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = colorResource(id = R.color.danger)
                                )
                            ){
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Emergency icon",
                                    )

                                    Spacer(modifier = Modifier.padding(top = 10.dp))

                                    Text(
                                        text = "Darurat",
                                        style = TextStyle(
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }

                            Card(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .width(120.dp)
                                    .fillMaxSize()
                                    .clickable {
                                        val intent = Intent(context, NormalActivity::class.java)
                                        activity?.startActivity(intent)
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = colorResource(id = R.color.warning)
                                )
                            ){
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = "admin icon",
                                    )

                                    Spacer(modifier = Modifier.padding(top = 10.dp))

                                    Text(
                                        text = "Normal",
                                        style = TextStyle(
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }

                            Card(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .width(120.dp)
                                    .fillMaxSize()
                                    .clickable {
                                        val intent = Intent(context, SuccessfullActivity::class.java)
                                        activity?.startActivity(intent)
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = colorResource(id = R.color.success)
                                )
                            ){
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "user icon",
                                        tint = Color.Black
                                    )

                                    Spacer(modifier = Modifier.padding(top = 10.dp))

                                    Text(
                                        text = "Selesai",
                                        style = TextStyle(
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.padding(top = 20.dp))

            Text(
                text = "Berita",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.padding(top = 10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                // berita at here
            }

            Spacer(modifier = Modifier.padding(top = 20.dp))

            Text(
                text = "Kota Saran",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.padding(top = 10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                // berita at here
            }


        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        AdminView()
    }
}