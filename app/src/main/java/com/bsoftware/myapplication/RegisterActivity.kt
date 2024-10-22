package com.bsoftware.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bsoftware.myapplication.bcrypt.BcryptHashPassword
import com.bsoftware.myapplication.dataclass.CreateUserDataClass
import com.bsoftware.myapplication.dialogalert.AlertDialogCustom
import com.bsoftware.myapplication.dialogalert.DatePickerCustomDialog
import com.bsoftware.myapplication.firebase.FirebaseAuthentication
import com.bsoftware.myapplication.idgenerator.idGenerator
import com.bsoftware.myapplication.preferencedatastore.UserDataDatastore
import com.bsoftware.myapplication.sharepref.UserLoginSharePref
import com.bsoftware.myapplication.ui.theme.MyApplicationTheme
import com.bsoftware.myapplication.viewmodel.LoginStateViewModel
import com.bsoftware.myapplication.viewmodel.UserDataViewModel
import com.bsoftware.myapplication.viewmodel.viewmodelprovider.LoginViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                    val userLoginSharePref = UserLoginSharePref(this)
                    val viewModel = ViewModelProvider(this, LoginViewModelFactory(userLoginSharePref)).get(LoginStateViewModel::class.java)
                    val userDataViewModel : UserDataViewModel by viewModels()

                    FormRegister(viewModel, userDataViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormRegister(
    viewModel : LoginStateViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    userDataViewModel : UserDataViewModel =  androidx.lifecycle.viewmodel.compose.viewModel()
){
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
    val generateId = idGenerator().generateRandomId(12)
    val cryptPassword = BcryptHashPassword()

    val response by userDataViewModel.response.observeAsState()
    var showStatusDialog by remember { mutableStateOf(false) }

    var showDialogTitle by remember { mutableStateOf("") }
    var showDialogMessage by remember { mutableStateOf("") }
    var job : Job? = null

    var showLoading by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_signup_new1),
            contentDescription = "Background Login",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, end = 15.dp)
        ) {

            // title text
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {

                Text(
                    text = "siPADUKA",
                    style = TextStyle(
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )

                Text(
                    text = "Daftar",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 5.dp)
                )
            }

            Spacer(modifier = Modifier.padding(top = 10.dp))
            // fullname textfield
            OutlinedTextField(
                value = fullName,
                onValueChange = {fullName = it},
                label = {
                    Text(text = "Nama Lengkap")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )

            // idNumber textfield
            OutlinedTextField(
                value = idNumber,
                onValueChange = {idNumber = it},
                label = {
                    Text(text = "No. KTP")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                shape = RoundedCornerShape(15.dp)
            )

            // Address textfield
            OutlinedTextField(
                value = address,
                onValueChange = {address = it},
                label = {
                    Text(text = "Alamat")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                shape = RoundedCornerShape(15.dp)
            )

            // PhoneNum textfield
            OutlinedTextField(
                value = phoneNum,
                onValueChange = {phoneNum = it},
                label = {
                    Text(text = "No.HP")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                shape = RoundedCornerShape(15.dp)
            )

            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(170.dp)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {expanded = !expanded}
                    ) {
                        OutlinedTextField(
                            value = selectedSex,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
                            modifier = Modifier.menuAnchor(),
                            shape = RoundedCornerShape(15.dp)
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


                OutlinedTextField(
                    value = birthDay,
                    onValueChange = {birthDay = it},
                    readOnly = true,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .clickable {
                            showDialogDate = true
                        },
                    leadingIcon = {
                        Icons.Rounded.DateRange
                    },
                    enabled = false,
                    shape = RoundedCornerShape(15.dp)
                )

                if(showDialogDate){
                    DatePickerCustomDialog(onDateSelected = {birthDay = it}, onDismiss = {showDialogDate = false})
                }
            }

            // email textfield
            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                label = {
                    Text(text = "Email")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
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

            Row{
                Button(
                    onClick = {
                        if(fullName.isNotEmpty() || idNumber.isNotEmpty() || address.isNotEmpty() || phoneNum.isNotEmpty() || email.isNotEmpty() || password.isNotEmpty() || birthDay.isNotEmpty() || selectedSex.isNotEmpty()){

                            job?.cancel()
                            showLoading = true

                            job = CoroutineScope(Dispatchers.IO).launch {
                                var success = false

                                while(!success){
                                    try {
                                        userDataViewModel.createUserData(
                                            id = generateId,
                                            name = fullName,
                                            email = email,
                                            nohp = phoneNum,
                                            alamat = address,
                                            verified_at = "timestamp at here",
                                            token = "",
                                            is_admin = "0",
                                            password = cryptPassword.hashPassword(password),
                                            remember_token = "",
                                            created_at = "",
                                            updated_at = ""
                                        )
                                    } catch (e : Exception){
                                        Log.e("Create User Data Response", e.message.toString())
                                    }

                                    // check response at here
                                    when(response?.get(0)?.statusCode){
                                        "201" -> {
                                            showLoading = false
                                            success = true

                                            Log.d("response", response?.get(0)?.status.toString())

                                            withContext(Dispatchers.Main){
                                                viewModel.setLoginState(true)

                                                // Store data at datastore
                                                CoroutineScope(Dispatchers.IO).launch {
                                                    UserDataDatastore(context).storeUserDataProfile(
                                                        generateId,
                                                        fullName,
                                                        idNumber,
                                                        address,
                                                        phoneNum,
                                                        email,
                                                        birthDay,
                                                        selectedSex
                                                    )
                                                }

                                                val intent = Intent(context,LoginActivity::class.java)
                                                context.startActivity(intent)
                                                activity.finish()
                                            }
                                        }
                                        "404" -> {
                                            showLoading = false
                                            withContext(Dispatchers.Main){
                                                Log.e("response", response?.get(0)?.status.toString())
                                                showStatusDialog = true
                                                showDialogTitle = "Error 404"
                                                showDialogMessage = response?.get(0)?.message.toString()
                                            }
                                        }
                                        "500" -> {
                                            showLoading = false
                                            withContext(Dispatchers.Main){
                                                Log.e("response", response?.get(0)?.status.toString())
                                                showStatusDialog = true
                                                showDialogTitle = "Error 500"
                                                showDialogMessage = response?.get(0)?.message.toString()
                                            }
                                        }
                                    }
                                    break
                                }
                            }

                        } else {
                            Toast.makeText(context,"Please Fill All Field", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .padding(top = 10.dp)
                ) {
                    Text(text = "Daftar")
                }

                if(showStatusDialog){
                    AlertDialogCustom(
                        title = showDialogTitle,
                        message = showDialogMessage,
                        onDismiss = {
                            showStatusDialog = false
                        },
                        onAgreeClickButton = {
                            showStatusDialog = false
                        }
                    )
                }

                // Button Login
                // Button Register
                Button(
                    onClick = {
                        // intent into login activity
                        val intent = Intent(context,LoginActivity::class.java)
                        activity.startActivity(intent)
                        activity.finish()
                    },
                    modifier = Modifier
                        .padding(top = 10.dp, start = 5.dp)
                ) {
                    Text(text = "Login")
                }
            }
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