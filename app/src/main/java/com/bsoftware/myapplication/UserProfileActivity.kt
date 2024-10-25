package com.bsoftware.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bsoftware.myapplication.dataclass.CreateUserDataClass
import com.bsoftware.myapplication.dataclass.UserData
import com.bsoftware.myapplication.preferencedatastore.UserDataDatastore
import com.bsoftware.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class UserProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserProfileView()
                }
            }
        }
    }
}

@Composable
fun UserProfileView() {
    val context : Context = LocalContext.current
    val userDataStorage = UserDataDatastore(context)
    //get data storage
    val getDataUser by userDataStorage.getUserDataProfileFlow.collectAsState(initial = UserData())

    // view code
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Text(
            text = "Profile",
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 30.sp
            )
        )

        Spacer(modifier = Modifier.padding(top = 5.dp))

       /* Text(
            text = getDataUser.fullname,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )*/

        Spacer(modifier = Modifier.padding(top = 20.dp))
        // then, we get a data use index access to
        UserProfileViewDataCard(
            getDataUser
        )
    }
}

@Composable
fun UserProfileViewDataCard(userData : UserData){
// content of userdataview such : Idnum, address, phonenum, birthdaydate, email
// card profile
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
                text = "Test",
                modifier = Modifier
                    .padding(top = 3.dp)
            )

            // Address
            Text(
                text = "Test",
                modifier = Modifier
                    .padding(top = 3.dp)
            )

            // Phonenum
            Text(
                text = "Test",
                modifier = Modifier
                    .padding(top = 3.dp)
            )

            // Bithday date
            Text(
                text = "Test",
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

    Spacer(modifier = Modifier.padding(top = 20.dp))

    Button(
        onClick = {

        },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = "Logout")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserProfileActivityPreview() {
    MyApplicationTheme {
        UserProfileView()
    }
}
