package com.bsoftware.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bsoftware.myapplication.dataclass.CreateUserDataClass
import com.bsoftware.myapplication.firebase.FirebaseAuthentication
import com.bsoftware.myapplication.sharepref.UserLoginSharePref
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

                    if(UserLoginSharePref(this).getStatus()){
                        // if user done for login (or true login), we gonna intent and get a data
                        val intent = Intent(context,MainActivity::class.java)
                        this.startActivity(intent)
                        this.finish()

                    } else {
                        // if a user login is false
                        FormLogin()
                    }
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
            .padding(start = 20.dp, end = 20.dp)
    ) {
        // make section for header
        Image(
            painter = painterResource(id = R.drawable.logogaruda_new),
            contentDescription = "Logo",
            modifier = Modifier
                .width(90.dp)
                .height(90.dp)
        )

        Spacer(modifier = Modifier.padding(top = 10.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Text(
                text = "siDuKa",
                style = TextStyle(
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold
                ),
            )

            Text(
                text = "Login",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(top = 5.dp)
            )
        }

        Spacer(modifier = Modifier.padding(top = 10.dp))

        // email textfield
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = {
                Text(text = "Email")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp)
        )

        // password textfield
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = {
                Text(text = "Password")
            },
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp)
        )

        Spacer(modifier = Modifier.padding(top = 10.dp))

        // Button Login
        Button(
            onClick = {
                if(email.isNotEmpty() && password.isNotEmpty()){
                    firebaseAuthentication.loginUser(
                        email = email,
                        password = password,
                        activity = activity,
                        onSuccess = {
                            activity.startActivity(Intent(context,MainActivity::class.java))
                            activity.finish()
                            UserLoginSharePref(activity).setStateLogin(true)
                        },
                        onFailed = {
                            Toast.makeText(context,"Login Fail, Please Try Again", Toast.LENGTH_SHORT).show()
                        },
                        context
                    )
                } else {
                    Toast.makeText(context,"Please Fill All Field", Toast.LENGTH_SHORT).show()
                }

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
fun LoginActivityPreview() {
    MyApplicationTheme {
        FormLogin()
    }
}