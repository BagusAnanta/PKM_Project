package com.bsoftware.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bsoftware.myapplication.dataclass.CreateUserDataClass
import com.bsoftware.myapplication.firebase.FirebaseAuthentication
import com.bsoftware.myapplication.ui.theme.MyApplicationTheme

val firebaseAuthentication : FirebaseAuthentication = FirebaseAuthentication()

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context : Context = LocalContext.current

                    firebaseAuthentication.checkUserLogin(
                        onLogin = {
                            val intent = Intent(context,MainActivity::class.java)
                            this.startActivity(intent)
                        },
                        onFailLogin = {
                            // empty fail login
                        }
                    )
                    FormLogin()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormLogin(){
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    val activity : Activity = (LocalContext.current as Activity)
    val context : Context = LocalContext.current



    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp)
    ) {
        // email textfield
        TextField(
            value = email,
            onValueChange = {email = it},
            label = {
                Text(text = "Email")
            },
            modifier = Modifier.fillMaxWidth()
        )

        // password textfield
        TextField(
            value = password,
            onValueChange = {password = it},
            label = {
                Text(text = "Password")
            },
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        )

        // Button Login
        Button(
            onClick = {
                firebaseAuthentication.loginUser(
                    email = email,
                    password = password,
                    activity = activity,
                    onSuccess = {
                        activity.startActivity(Intent(context,MainActivity::class.java))
                        activity.finish()
                    },
                    onFailed = {
                        Toast.makeText(context,"Login Fail, Please Try Again", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Login")
        }

        // Button Register
        Button(
            onClick = {
                activity.startActivity(Intent(context,RegisterActivity::class.java))
                activity.finish()
            },
            modifier = Modifier
                .padding(top = 3.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Daftar")
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        FormLogin()
    }
}