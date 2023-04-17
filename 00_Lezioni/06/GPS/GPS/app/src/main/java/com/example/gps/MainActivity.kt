package com.example.gps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.gps.ui.theme.GPSTheme
import com.google.android.gms.location.*

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private var requestingLocationUpdates = false

    private lateinit var locationPermissionRequest: ActivityResultLauncher<String>

    private var showSnackBar = mutableStateOf(false)
    private var showAlertDialog = mutableStateOf(false)
    private val location = mutableStateOf(LocationDetails(0.toDouble(), 0.toDouble()))

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                startLocationUpdates()
            } else {
                showSnackBar.value = true
            }
        }

        locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).apply {
                setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            }.build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                location.value = LocationDetails(
                    p0.locations.last().latitude,
                    p0.locations.last().longitude
                )
                stopLocationUpdates()
                requestingLocationUpdates = false
            }
        }

        setContent {
            GPSTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val snackbarHostState = remember { SnackbarHostState() }
                    Scaffold(
                        snackbarHost = { SnackbarHost(snackbarHostState) },
                        content = { innerPadding ->
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                                    .padding(15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Button(onClick = {
                                    startLocationUpdates()
                                }) {
                                    Text(text = "Get current location")
                                }

                                Text(text = "Latitude : " + location.value.latitude)
                                Text(text = "Longitude : " + location.value.longitude)
                            }
                        }
                    )

                    if (showSnackBar.value) {
                        SnackBarComposable(snackbarHostState, this, showSnackBar)
                    }
                    if (showAlertDialog.value) {
                        AlertDialogComposable(this, showAlertDialog)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (requestingLocationUpdates) startLocationUpdates()
    }

    private fun startLocationUpdates() {
        requestingLocationUpdates = true
        val permission = Manifest.permission.ACCESS_COARSE_LOCATION

        when {
            //permission already granted
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                val gpsEnabled = checkGPS()
                if (gpsEnabled) {
                    fusedLocationProviderClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.getMainLooper()
                    )
                } else {
                    showAlertDialog.value = true
                }

            }
            //permission already denied
            shouldShowRequestPermissionRationale(permission) -> {
                showSnackBar.value = true
            }
            else -> {
                //first time: ask for permissions
                locationPermissionRequest.launch(
                    permission
                )
            }
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun checkGPS(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }
}