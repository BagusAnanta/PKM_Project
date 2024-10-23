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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.bsoftware.myapplication.bcrypt.BcryptHashPassword
import com.bsoftware.myapplication.dialogalert.AlertDialogCustom
import com.bsoftware.myapplication.firebase.FirebaseAuthentication
import com.bsoftware.myapplication.preferencedatastore.UserDataDatastore
import com.bsoftware.myapplication.sharepref.UserLoginSharePref
import com.bsoftware.myapplication.ui.theme.MyApplicationTheme
import com.bsoftware.myapplication.viewmodel.LoginStateViewModel
import com.bsoftware.myapplication.viewmodel.UserDataViewModel
import com.bsoftware.myapplication.viewmodel.viewmodelprovider.LoginViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                    val userLoginSharePref = UserLoginSharePref(this)
                    val viewModel = ViewModelProvider(this,LoginViewModelFactory(userLoginSharePref)).get(LoginStateViewModel::class.java)

                    FormLogin(viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormLogin(
    viewModel : LoginStateViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    userDataViewModel : UserDataViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    var getId by remember{ mutableStateOf("") }
    var getName by remember{ mutableStateOf("") }
    var getEmail by remember{ mutableStateOf("") }
    var getPhoneNum by remember { mutableStateOf("") }
    var getAddress by remember { mutableStateOf("") }
    var getVerifiedAt by remember { mutableStateOf("") }
    var getToken by remember { mutableStateOf("") }
    var getIsAdmin by remember { mutableStateOf("") }
    var getPassword by remember { mutableStateOf("") }
    var getRememberToken by remember { mutableStateOf("") }
    var getCreateAt by remember { mutableStateOf("") }
    var getUpdateAt by remember { mutableStateOf("") }
    val activity : Activity = (LocalContext.current as Activity)
    val context : Context = LocalContext.current

    val isLogginIn by viewModel.isLoginIn.collectAsState()
    val dataUser by userDataViewModel.userData.observeAsState(emptyList())
    val response by userDataViewModel.response.observeAsState()

    var showStatusDialog by remember { mutableStateOf(false) }
    var showLoading by remember { mutableStateOf(false) }

    val bcryptHashPassword = BcryptHashPassword()

    var showDialogTitle by remember { mutableStateOf("") }
    var showDialogDescription by remember { mutableStateOf("") }

    var job : Job? = null


    // check login first
    if(isLogginIn){
        // if user done for login (or true login), we gonna intent and get a data
        val intent = Intent(context,MainActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }
    
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.background_login_new),
            contentDescription = "Background Login",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )

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
                    text = "siPADUKA",
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

                        job?.cancel()

                        showLoading = true

                        job = CoroutineScope(Dispatchers.IO).launch {
                            var success = false

                            while(!success){
                                userDataViewModel.readUserData(email)
                                try{
                                    dataUser.forEach{ data ->
                                        getId = data.id
                                        getName = data.name
                                        getEmail = data.email
                                        getPhoneNum = data.nohp
                                        getAddress = data.alamat
                                        getVerifiedAt = data.verified_at
                                        getToken = data.token
                                        getIsAdmin = data.is_admin
                                        getPassword = data.password
                                        getRememberToken = data.remember_token
                                        getCreateAt = data.created_at
                                        getUpdateAt = data.updated_at
                                    }
                                } catch (e : NullPointerException){
                                    Log.e("error", e.message.toString())
                                }

                                Log.d("getPassword", getPassword)
                                val checkValidationPassword = bcryptHashPassword.checkPassword(password,getPassword)

                                if(checkValidationPassword){
                                    when(response?.get(0)?.statusCode){
                                        "200" -> {
                                            showLoading = false
                                            Log.d("response", response?.get(0)?.status.toString())
                                            success = true

                                            withContext(Dispatchers.Main){
                                                activity.startActivity(Intent(context,MainActivity::class.java))
                                                activity.finish()

                                                CoroutineScope(Dispatchers.IO).launch {
                                                    UserDataDatastore(context).storeUserDataProfile(
                                                        id = getId,
                                                        name = getName,
                                                        email = getEmail,
                                                        phoneNumber = getPhoneNum,
                                                        address = getAddress,
                                                        verifiedAt = getVerifiedAt,
                                                        token = getToken,
                                                        isAdmin = getIsAdmin,
                                                        rememberToken = getRememberToken,
                                                        createdAt = getCreateAt,
                                                        updatedAt = getUpdateAt
                                                    )
                                                }

                                                // set login state to true
                                                viewModel.setLoginState(true)

                                                Toast.makeText(context,"Login Berhasil, selamat datang", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                        "404" -> {
                                            showLoading = false
                                            withContext(Dispatchers.Main){
                                                Log.e("response", response?.get(0)?.status.toString())
                                                showStatusDialog = true
                                                showDialogTitle = "Email atau Password Salah"
                                                showDialogDescription = "Mohon Check Kembali Email atau Password Anda"
                                            }

                                        }
                                        "500" -> {
                                            showLoading = false
                                            withContext(Dispatchers.Main){
                                                Log.e("response", "Internal Server Error")
                                                showStatusDialog = true
                                                showDialogTitle = "Error 500"
                                                showDialogDescription = "Kesalahan sisi server, mohon ulangi kembali"
                                            }
                                        }
                                    }
                                } else {

                                    when(response?.get(0)?.statusCode){
                                        "500" -> {
                                            showLoading = false
                                            withContext(Dispatchers.Main){
                                                Log.e("response", "Internal Server Error")
                                                showStatusDialog = true
                                                showDialogTitle = "Error 500"
                                                showDialogDescription = "Kesalahan sisi server, mohon ulangi kembali"
                                            }
                                        }
                                    }

                                    showLoading = false
                                    withContext(Dispatchers.Main){
                                        Log.e("response", "Wrong Password")
                                        showStatusDialog = true
                                        showDialogTitle = "Password Salah"
                                        showDialogDescription = "Password yang anda masukkan salah, mohon ulangi kembali"

                                        success = true
                                    }
                                }
                            }
                            delay(1000L)
                        }

                        // i want make for admin login, in a activity, so we can custome user and password for admin like this
                        if(getIsAdmin.equals("1")){
                            val intent = Intent(context,AdminActivity::class.java)
                            activity.startActivity(intent)
                            activity.finish()

                        }

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

            if(showLoading){
                CircularProgressIndicator(modifier = Modifier.padding(top = 10.dp))
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

            if(showStatusDialog){
                AlertDialogCustom(
                    title = showDialogTitle,
                    message = showDialogDescription,
                    onDismiss = {
                        showStatusDialog = false
                    },
                    onAgreeClickButton = {
                        showStatusDialog = false
                    }
                )
            }

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