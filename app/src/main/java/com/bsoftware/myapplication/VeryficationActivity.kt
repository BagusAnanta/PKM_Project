package com.bsoftware.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bsoftware.myapplication.ui.theme.MyApplicationTheme

class VeryficationActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                VerificationPreview()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationPreview(modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current
    // val activity = LocalContext.current as ComponentActivity

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Verifikasi",
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
                CardContain()
            }
       }
    }
}

@Composable
fun CardContain(modifier : Modifier = Modifier){
    val isValid by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.padding(10.dp)
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            elevation = 20.dp,
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
            ) {
                Column {
                    Row {
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
                            text = "baguswijaya@gmail.com"
                        )
                    }

                    Row{
                        Text(
                            text = "No Handphone :",
                            modifier = Modifier.padding(end = 5.dp)
                        )
                        
                        Text(
                            text = "081234567890"
                        )
                    }


                    Row{
                        Text(
                            text = "Alamat :",
                            modifier = Modifier.padding(end = 5.dp)
                        )

                        Text(
                            text = " Jl. kebun anggur No. 1"
                        )
                    }

                    Row{
                        Text(
                            text = "Dibuat :",
                            modifier = Modifier.padding(end = 5.dp)
                        )

                        Text(
                            text = "1/2/2024 12:00:40"
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
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = if(isValid) colorResource(id = R.color.success) else colorResource(id = R.color.danger)
                        )
                    ) {
                        Text(
                            text = "Verifikasi",
                        )
                    }

                    Button(
                        onClick = { /*TODO*/ },
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.warning)
                        )
                    ) {
                        Text(text = "Edit")
                    }

                    Button(
                        onClick = { /*TODO*/ },
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
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
fun GreetingPreview4() {
    MyApplicationTheme {
        VerificationPreview()
    }
}