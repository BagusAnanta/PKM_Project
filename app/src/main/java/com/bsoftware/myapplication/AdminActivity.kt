package com.bsoftware.myapplication

import android.os.Bundle
import android.service.autofill.UserData
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
   Column {
       Row(
           horizontalArrangement = Arrangement.Absolute.Center,
           modifier = Modifier
               .fillMaxWidth()
               .padding(top = 10.dp)
       ) {
           Text(text = "Admin Activity")
       }

       Spacer(modifier = Modifier.padding(top = 5.dp))

       LazyColumn {
           item {

           }
       }
   }
}

@Composable
fun AdminCardInfo(userData : CreateUserDataClass){
   Column {
       Card(
           modifier = Modifier
               .fillMaxWidth()
               .height(160.dp),
           shape = RoundedCornerShape(10.dp),
           colors = CardDefaults.cardColors(
               containerColor = Color.Gray
           )
       ) {
           Column(
               modifier = Modifier
                   .padding(5.dp)
           ) {
               // Idnumber
               Text(
                   text = userData.idNumber,
                   modifier = Modifier
                       .padding(top = 3.dp)
               )

               // Address
               Text(
                   text = userData.address,
                   modifier = Modifier
                       .padding(top = 3.dp)
               )

               // Phonenum
               Text(
                   text = userData.phoneNumber,
                   modifier = Modifier
                       .padding(top = 3.dp)
               )

               // Bithday date
               Text(
                   text = userData.birthday,
                   modifier = Modifier
                       .padding(top = 3.dp)
               )

               // Email
               Text(
                   text = userData.email,
                   modifier = Modifier
                       .padding(top = 3.dp)
               )
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