package com.bsoftware.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bsoftware.myapplication.ui.theme.MyApplicationTheme

class UserAccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
               UserAccountPreview()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAccountPreview(modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current
    // val activity = LocalContext.current as ComponentActivity

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Akun User",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        /*val intent = Intent(context, AdminActivity::class.java)
                        context.startActivity(intent)
                        activity.finish()*/
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding
        ) {
            item {
                CardContainUserAccount()
            }
        }
    }
}

@Composable
fun CardContainUserAccount(modifier : Modifier = Modifier){

    Column(
        modifier = Modifier.padding(10.dp)
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp),
            elevation = 20.dp,
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
            ) {
                Column {
                    Row{
                        Text(
                            text = "Nama :",
                            modifier = Modifier.padding(end = 5.dp)
                        )
                        
                        Text(
                            text = "Bagus Wijaya"
                        )
                    }

                    Row{

                        Text(
                            text = "Email :",
                            modifier = Modifier.padding(end = 5.dp)
                        )

                        Text(
                            text = "admin@gmail.com"
                        )
                    }

                    Row{

                        Text(
                            text = "No Handphone :",
                            modifier = Modifier.padding(end = 5.dp)
                        )

                        Text(
                            text = "08123456788"
                        )
                    }

                    Row{
                        Text(
                            text = "Alamat :",
                            modifier = Modifier.padding(end = 5.dp)
                        )

                        Text(
                            text = "Jl. kebun Naga No. 2"
                        )
                    }

                    Row{
                        Text(
                            text = "Verifikasi Akun :",
                            modifier = Modifier.padding(end = 5.dp)
                        )

                        Text(
                            text = "3/1/2024 10:00:30"
                        )
                    }

                    Row{
                        Text(
                            text = "Dibuat :",
                            modifier = Modifier.padding(end = 5.dp)
                        )

                        Text(
                            text = "1/1/2024 13:00:40"
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {

                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.warning)
                        )
                    ) {
                        Text(text = "Edit")
                    }

                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.danger)
                        )
                    ) {
                        Text(text = "Hapus")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview6() {
    MyApplicationTheme {
        UserAccountPreview()
    }
}