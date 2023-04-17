package com.example.gpsempty

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.gpsempty.ui.theme.GPSEmptyTheme

class MainActivity : ComponentActivity() {

    private var showSnackBar = mutableStateOf(false)
    private var showAlertDialog = mutableStateOf(false)
    private val location = mutableStateOf(LocationDetails(0.toDouble(), 0.toDouble()))

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GPSEmptyTheme {
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
        TODO()
    }

    private fun startLocationUpdates() {
        TODO()
    }

    private fun stopLocationUpdates() {
        TODO()
    }

    private fun checkGPS(): Boolean {
        TODO()
    }

    override fun onPause() {
        super.onPause()
        TODO()
    }
}