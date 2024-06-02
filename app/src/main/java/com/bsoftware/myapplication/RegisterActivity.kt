package com.bsoftware.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bsoftware.myapplication.dataclass.CreateUserDataClass
import com.bsoftware.myapplication.dialogalert.DatePickerCustomDialog
import com.bsoftware.myapplication.firebase.FirebaseAuthentication
import com.bsoftware.myapplication.ui.theme.MyApplicationTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FormRegister()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormRegister(){
    var fullName by remember{ mutableStateOf("") }
    var idNumber by remember{ mutableStateOf("") }
    var address by remember{ mutableStateOf("") }
    var phoneNum by remember{ mutableStateOf("") }
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    var birthDay by remember{ mutableStateOf("Tanggal Lahir") }

    val context : Context = LocalContext.current
    val activity : Activity = (LocalContext.current as Activity)
    val sexList = arrayOf("Laki-laki","Perempuan")
    var expanded by remember{ mutableStateOf(false) }
    var selectedSex by remember{ mutableStateOf(sexList[0]) }

    var showDialogDate by remember{ mutableStateOf(false) }

    val firebaseAuthentication : FirebaseAuthentication = FirebaseAuthentication()


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp)
    ) {
        // fullname textfield
        TextField(
            value = fullName,
            onValueChange = {fullName = it},
            label = {
                Text(text = "Nama Lengkap")
            },
            modifier = Modifier.fillMaxWidth()
        )

        // idNumber textfield
        TextField(
            value = idNumber,
            onValueChange = {idNumber = it},
            label = {
                Text(text = "No. KTP")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        )

        // Address textfield
        TextField(
            value = address,
            onValueChange = {address = it},
            label = {
                Text(text = "Alamat")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        )

        // PhoneNum textfield
        TextField(
            value = phoneNum,
            onValueChange = {phoneNum = it},
            label = {
                Text(text = "No.HP")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        )

        Row(
            modifier = Modifier
                .padding(top = 5.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(170.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {expanded = !expanded}
                ) {
                    TextField(
                        value = selectedSex,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        sexList.forEach {item ->
                            DropdownMenuItem(
                                text = { Text(text = item)},
                                onClick = {
                                    // in here we save a selectedText
                                    selectedSex = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }


            TextField(
                value = birthDay,
                onValueChange = {birthDay = it},
                readOnly = true,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .clickable {
                        showDialogDate = true
                    },
                trailingIcon = {
                    Icons.Outlined.Add
                },
                enabled = false
            )

            if(showDialogDate){
                DatePickerCustomDialog(onDateSelected = {birthDay = it}, onDismiss = {showDialogDate = false})
            }
        }

        // email textfield
        TextField(
            value = email,
            onValueChange = {email = it},
            label = {
                Text(text = "Email")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        )

        // password textfield
        TextField(
            value = password,
            onValueChange = {password = it},
            label = {
                Text(text = "Password")
            },
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
        )

        // Button Register
        Button(
            onClick = {
                firebaseAuthentication.createDataUser(
                    userData = CreateUserDataClass(
                        fullname = fullName,
                        idNumber = idNumber,
                        address = address,
                        phoneNumber = phoneNum,
                        email = email,
                        birthday = birthDay,
                        sex = selectedSex
                    ),
                    password,
                    activity = activity,
                    onSuccess = {
                        Toast.makeText(context,"Register User Successfull", Toast.LENGTH_SHORT).show()
                        activity.startActivity(Intent(context,MainActivity::class.java))
                        activity.finish()
                    },
                    onFailed = {
                        Toast.makeText(context,"You Email Already Register", Toast.LENGTH_SHORT).show()
                    },
                    context = context
                )
            },
            modifier = Modifier
                .padding(top = 3.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Daftar")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun RegisterActivityPreview() {
    MyApplicationTheme {
        FormRegister()
    }
}