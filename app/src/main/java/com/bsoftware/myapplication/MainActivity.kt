 package com.bsoftware.myapplication

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bsoftware.myapplication.dataclass.CreateLocationDataClass
import com.bsoftware.myapplication.dataclass.LocationData
import com.bsoftware.myapplication.dialogalert.AlertDialogCustom
import com.bsoftware.myapplication.firebase.FirebaseLocationSend
import com.bsoftware.myapplication.preferencedatastore.UserDataDatastore
import com.bsoftware.myapplication.sealedclass.Screen
import com.bsoftware.myapplication.sharepref.UidSharePref
import com.bsoftware.myapplication.ui.theme.MyApplicationTheme
import com.bsoftware.myapplication.viewmodel.UserLocationViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.NullPointerException

 var fusedLocationProviderClient : FusedLocationProviderClient? = null
 var locationCallback : LocationCallback? = null
 var location : Location? = null
 var locationRequest : LocationRequest? = null
 var settingsClient : SettingsClient? = null
 var locationSettingRequest : LocationSettingsRequest? = null
 var latitude : Double = 0.0
 var longitude : Double = 0.0
 var fetchAddress : String = ""

 val items = listOf(
     Screen.Home,
     Screen.Profile
 )

 class MainActivity : ComponentActivity() {
     private val locationUpdateViewModel : UserLocationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
          *  Minimum Variable Product
          *  1. first, we make a permission request for gps and ran in first
          *  2. After gps on, we can get a longitude and latitude data
          *
          *  for future feature
          *  - If user press panic button, gps turn on automatic and send a longitude and latitude data
          * */

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // request permission at here
        val permissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){ permission ->
            when{
                permission.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION,false) -> {
                    Log.d("Location Permission" ,"Precise location access granted")
                }

                permission.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION,false) -> {
                    Log.d("Location Permission", "Only approximate location access granted")
                }

                permission.getOrDefault(Manifest.permission.CAMERA,false) -> {
                    Log.d("Camera Permission", "Camera access granted")
                }

                permission.getOrDefault(Manifest.permission.WRITE_EXTERNAL_STORAGE,false) -> {
                    Log.d("Storage Permission", "Storage access granted")
                }

                else -> {
                    Log.d("Location Permission", "Permission Denied")
                }
            }
        }

        permissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // PanicButton()
                    // BottomBarWithFloatingButton()
                    ButtonOption(this,locationUpdateViewModel)
                }
            }
        }
    }

     private fun stopLocationUpdate(){
         // use for stop a gps
         try{
             fusedLocationProviderClient?.removeLocationUpdates(locationCallback!!)
                 ?.addOnCompleteListener {task ->
                     Log.d("StopLocationUpdate", task.toString())
                 }
         } catch (e : NullPointerException){
             // can NPE because, if user no use a gps, stopLocationUpdate return a null value
             Log.e("StopLocationUpdateError", e.toString())
         }
     }

     override fun onDestroy() {
         super.onDestroy()
         // stop location if a activity destroy
         try{
             stopLocationUpdate()
         } catch (e : NullPointerException){
             Log.e("DestroyLocationUpdate", "LocationUpdateNoExist,Exit")
         }
     }
}

 /*GPS SECTION
 * This GPS Section, this code contain a private function for gps */
 // in indonesian : Setiap HandPhone berpindah, gps akan selalu update lokasi, jika berhenti pada suatu tempat, maka tidak akan update
 private fun startLocationUpdate(context: Context){
     settingsClient?.checkLocationSettings(locationSettingRequest!!)
         ?.addOnSuccessListener {
             Log.d("startlocationupdate","Location Settings Ok")
             if(ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                 return@addOnSuccessListener;
             }

             fusedLocationProviderClient?.requestLocationUpdates(locationRequest!!, locationCallback!!, Looper.myLooper())
         }

         ?.addOnFailureListener { fail ->
             Log.e("OnStartLocationUpdate", fail.printStackTrace().toString())
         }
 }



 private fun initGPS(context: Context, lifecycleOwner: LifecycleOwner, locationUpdateViewModel: UserLocationViewModel){
     fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
     settingsClient = LocationServices.getSettingsClient(context)
     locationCallback = object : LocationCallback() {
         override fun onLocationResult(locationResult: LocationResult) {
             super.onLocationResult(locationResult)
             getLocation(locationResult = locationResult,context, lifecycleOwner, locationUpdateViewModel)
         }
     }

     locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,5000)
         .setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
         .setMinUpdateIntervalMillis(500)
         .setMinUpdateDistanceMeters(1f)
         .setWaitForAccurateLocation(true)
         .build()

     val builder : LocationSettingsRequest.Builder = LocationSettingsRequest.Builder()
     builder.addLocationRequest(locationRequest!!)
     locationSettingRequest = builder.build()

     startLocationUpdate(context)
 }

 private fun getLocation(locationResult : LocationResult,context: Context, lifecycleOwner : LifecycleOwner, locationUpdateViewModel : UserLocationViewModel){

     var uidUser : String = "AxBCtjd7fsy5fdgat5"

     val activity = (context as Activity)
     location = locationResult.lastLocation

     Log.d("Location","latitude ${location?.latitude}")
     Log.d("Location","longitude ${location?.longitude}")
     Log.d("Location","altitude ${location?.altitude}")

     latitude = location?.latitude!!
     longitude = location?.longitude!!

     // for address
     try{
         val geolocation = Geocoder(context,Locale.getDefault())
         val address = geolocation.getFromLocation(
             latitude,
             longitude,
             1
         )

         fetchAddress = address?.get(0)?.getAddressLine(0).toString()
         Log.d("Location", fetchAddress)
     } catch (e : Exception){
         e.printStackTrace()
     }

     CoroutineScope(Dispatchers.IO).launch {
         // in here we gonna get UidUser
         UserDataDatastore(context).getUidUser.collect{
             uidUser = it

             Log.d("UidUserLocation", uidUser)
         }
     }

     // read response for location
     locationUpdateViewModel.readUserLocationByUid(uidUser)
     locationUpdateViewModel.response.observe(lifecycleOwner, Observer {
         if(it.isEmpty()){
             Log.e("LocationResponse", "Response Empty")
         } else {
             it.forEach {data ->
                 Log.d("StatusCode", data.statusCode)
                 Log.d("Status", data.status)

                 // set first for Location
                 locationUpdateViewModel.createUserLocation(
                     uidUser = uidUser,
                     longitude = longitude.toString(),
                     latitude = latitude.toString(),
                     address = fetchAddress
                 )

                 val locationTemp : MutableList<LocationData> = mutableListOf(
                     LocationData(
                         uidUser = uidUser,
                         longitude = longitude.toString(),
                         latitude = latitude.toString(),
                         address = fetchAddress
                     )
                 )

                 locationTemp.forEach { dataTemp ->
                     if(dataTemp.longitude != longitude.toString() && dataTemp.latitude != latitude.toString() && dataTemp.address != fetchAddress){
                         // if longitude, latitude, address it's not same like temp, we update
                         locationUpdateViewModel.updateUserLocation(
                             uidUser = uidUser,
                             longitude = longitude.toString(),
                             latitude = latitude.toString(),
                             address = fetchAddress
                         )
                     }
                 }
             }
         }
     })

     // save into firebase realtime database
     /*val locationDataClass = CreateLocationDataClass(UidSharePref(activity).getUid() ?: "",listOf(latitude,longitude), fetchAddress)
     FirebaseLocationSend().setLocationSend(locationDataClass, activity = activity)*/
 }

fun requestOnGPS(context : Context, lifecycleOwner: LifecycleOwner, locationUpdateViewModel: UserLocationViewModel){
    // in here we check a permission again, if a permission granted we turnon a gps
    // check a location
    if(ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
        // if a permission granted, we turn on a gps
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Log.w("LocationProviderEnable", "LocationProviderEnable")
        } else {
            // turn on gps use intent into settings
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        initGPS(context,lifecycleOwner,locationUpdateViewModel)
    }
}

@Composable
fun ButtonOption(lifecycleOwner: LifecycleOwner,locationUpdateViewModel: UserLocationViewModel){
    val context : Context = LocalContext.current
    var showGPSDialog by remember{ mutableStateOf(false) }
    var showPanicDialog by remember{ mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_mainmenu_new),
            contentDescription = "Background Login",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        val locationManager : LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

                        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                            // if a gps active we init a gps
                            initGPS(context, lifecycleOwner,locationUpdateViewModel)
                            showPanicDialog = true
                        } else {
                            showGPSDialog = true
                        }
                    },
                    shape = CircleShape,
                    modifier = Modifier
                        .size(150.dp,150.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Darurat",
                        style = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 25.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.padding(top = 20.dp))

                Button(
                    onClick = {
                        val intent = Intent(context, ReportActivity::class.java)
                        context.startActivity(intent)
                    },
                    shape = CircleShape,
                    modifier = Modifier
                        .size(150.dp,150.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Green,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Laporkan",
                        style = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 25.sp
                        )
                    )
                }
            }
        }

        if(showGPSDialog){
            // if GPS active, and user click panic button, we show a dialog
            AlertDialogCustom(
                title = "Perhatian",
                message = "Untuk Menggunakan Aplikasi,dibutuhkan GPS Apakah anda ingin mengaktifkan GPS ?",
                onDismiss =  {
                    showGPSDialog = false
                },
                onAgreeClickButton = {
                    // in here, we gonna turn gps
                    requestOnGPS(context,lifecycleOwner,locationUpdateViewModel)
                    showGPSDialog = false
                }
            )
        }

        if(showPanicDialog){
            // if a gps no active, we request a gps use AlertDialog for turn on a gps
            AlertDialogCustom(
                title = "Perhatian",
                message = "Petugas Akan Datang Sesuai Lokasi, Mohon Tunggu",
                onDismiss =  {
                    showPanicDialog = false
                },
                onAgreeClickButton = {
                    // in here, we gonna turn gps
                    showPanicDialog = false
                }
            )
        }
    }
}

/*@Composable
fun BottomBarWithFloatingButton(){
    val navController = rememberNavController()
    val context : Context = LocalContext.current
    var showGPSDialog by remember{ mutableStateOf(false) }
    var showPanicDialog by remember{ mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(60.dp)
               *//* cutoutShape = CircleShape,
                elevation = 22.dp*//*
            ){
               BottomNav(navController = navController)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // if condition, if a gps turn on, init a gps, if not turn on a gps first and run a initgps
                    val locationManager : LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

                    if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                        // if a gps active we init a gps
                        initGPS(context)
                        showPanicDialog = true
                    } else {
                        showGPSDialog = true
                    }
                },
                shape = CircleShape,
                contentColor = Color.White,
                modifier = Modifier
                    .offset(y = 50.dp)
            ) {
                Icon(imageVector = Icons.Filled.Warning, contentDescription = "Warning button")
            }
        }
    ){ it ->
        MainScreenNav(
            navController = navController,
            modifier = Modifier.padding(it)
        )
    }

    if(showGPSDialog){
        // if a gps no active, we request a gps use AlertDialog for turn on a gps
        AlertDialogCustom(
            title = "Perhatian",
            message = "Untuk Menggunakan Aplikasi,dibutuhkan GPS Apakah anda ingin mengaktifkan GPS ?",
            onDismiss =  {
                showGPSDialog = false
            },
            onAgreeClickButton = {
                // in here, we gonna turn gps
                requestOnGPS(context)
                showGPSDialog = false
            }
        )
    }

    if(showPanicDialog){
        AlertDialogCustom(
            title = "Mohon Tunggu",
            message = "Petugas Akan Datang Sesuai Lokasi Anda",
            onDismiss =  {
                showPanicDialog = false
            },
            onAgreeClickButton = {
                showPanicDialog = false
            }
        )
    }
}*/

/*@Composable
fun BottomNav(navController : NavController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    BottomNavigation(
        modifier = Modifier
            .height(100.dp),
        elevation = 0.dp
    ){
        items.forEach {
            BottomNavigationItem(
                icon = {
                    it.icon?.let { icon ->
                        Icon(
                            imageVector = icon,
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                label = {
                    it.title?.let {
                        Text(
                            text = it,
                            fontSize = 12.sp
                        )
                    }
                },
                selected = currentRoute?.hierarchy?.any { it.route == it.route } == true,
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.White.copy(alpha = 0.4f),
                onClick = {
                    it.route?.let{
                        navController.navigate(it){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun MainScreenNav(navController: NavHostController, modifier: Modifier = Modifier){

    NavHost(navController,startDestination = Screen.Home.route!!){

        // home
        composable(Screen.Home.route){
            // compose function at here
            ReportForm()
        }

        composable(Screen.Profile.route!!){
            // compose function at here
            UserProfileView()
        }
    }
}*/

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainPreview() {
    MyApplicationTheme {
        // PanicButton()
        // BottomBarWithFloatingButton()
        // ButtonOption(this)
    }
}