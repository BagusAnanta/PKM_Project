 package com.bsoftware.myapplication

import android.Manifest
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.bsoftware.myapplication.ui.theme.MyApplicationTheme
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient
import java.lang.NullPointerException
import java.util.Locale

 var fusedLocationProviderClient : FusedLocationProviderClient? = null
 var locationRequest : LocationRequest? = null
 var locationCallback : LocationCallback? = null
 var location : Location? = null
 var settingsClient : SettingsClient? = null
 var locationSettingRequest : LocationSettingsRequest? = null
 var latitude : Double = 0.0
 var longitude : Double = 0.0
 var longitudeStr : Float = 0.0f
 var latitudeStr : Float = 0.0f
 var fetchAddress : String = ""


 class MainActivity : ComponentActivity() {
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

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){ permission ->
            when{
                permission.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION,false) -> {
                    Log.d("Location Permission" ,"Precise location access granted")
                }

                permission.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION,false) -> {
                    Log.d("Location Permission", "Only approximate location access granted")
                }

                else -> {
                    Log.d("Location Permission", "Permission Denied")
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PanicButton()
                }
            }
        }
    }

     fun StartLocationUpdate(){
         settingsClient?.checkLocationSettings(locationSettingRequest!!)
             ?.addOnSuccessListener {
                 Log.d("startlocationupdate","Location Settings Ok")
                 if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                     return@addOnSuccessListener;
                 }
                 fusedLocationProviderClient?.requestLocationUpdates(locationRequest!!, locationCallback!!, Looper.myLooper())
             }

             ?.addOnFailureListener { fail ->
                 Log.e("OnStartLocationUpdate", "OnStartLocationUpdateError")
             }
     }

     fun stopLocationUpdate(){
         try{
             fusedLocationProviderClient?.removeLocationUpdates(locationCallback!!)
                 ?.addOnCompleteListener {task ->
                     Log.d("StopLocationUpdate", "StopLocationUpdate")
                 }
         } catch (e : NullPointerException){
             Log.e("StopLocationUpdateError", e.toString())
         }
     }

     fun getLocation(locationResult : LocationResult){
         location = locationResult.lastLocation

         Log.d("Location","latitude ${location?.latitude}")
         Log.d("Location","longitude ${location?.longitude}")
         Log.d("Location","altitude ${location?.altitude}")

         var s_lat = String.format(Locale.ROOT,"%.6f", location?.latitude)
         var s_log = String.format(Locale.ROOT,"%.6f", location?.longitude)

         latitude = location?.latitude!!
         longitude = location?.longitude!!

         // for address
         try{
             val geolocation = Geocoder(this,Locale.getDefault())
             val address = geolocation.getFromLocation(latitude, longitude,1)

             fetchAddress = address?.get(0)?.getAddressLine(0).toString()
             Log.d("Location", fetchAddress)
         } catch (e : Exception){
             e.printStackTrace()
         }
     }

     fun initGPS(){
         fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
         settingsClient = LocationServices.getSettingsClient(this)
         locationCallback = object : LocationCallback() {
             override fun onLocationResult(p0: LocationResult) {
                 super.onLocationResult(p0)
                 getLocation(locationResult = p0)
             }
         }
     }
}

@Composable
fun PanicButton() {
    val context : Context = LocalContext.current

   Button(onClick = { turnOnGPS(context) }) {
       Text(text = "Panic Button")
   }
}


fun turnOnGPS(context : Context) : Boolean{
    // in here we check a permission again, if a permission granted we turnon a gps automatic
    // check a location
    var isGPSOn = false
    if(ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
        // if a permission granted, we turn on a gps
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Log.w("LocationProviderEnable", "LocationProviderEnable")
        } else {
            // turn on gps use intent
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            isGPSOn = true
        }
    }
    return isGPSOn
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        PanicButton()
    }
}