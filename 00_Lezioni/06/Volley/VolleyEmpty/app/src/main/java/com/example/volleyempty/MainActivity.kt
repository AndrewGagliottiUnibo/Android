package com.example.volleyempty

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.volleyempty.ui.theme.VolleyEmptyTheme
import org.json.JSONObject

class MainActivity : ComponentActivity() {

    private var showConnectivitySnackBar = mutableStateOf(false)
    private val place = mutableStateOf("")

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VolleyEmptyTheme {
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
                                Text(text = "campus università cesena")
                                Button(onClick = {

                                }) {
                                    Text(text = "Get display_name")
                                }

                                Spacer(modifier = Modifier.height(15.dp))
                                Text(
                                    text = "Place: \n ${place.value}",
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    )

                    if (showConnectivitySnackBar.value) {
                        SnackBarConnectivityComposable(
                            snackbarHostState,
                            this,
                            showConnectivitySnackBar
                        )
                    }
                }
            }
        }
    }

    private fun sendRequest() {
        val url =
            "https://nominatim.openstreetmap.org/?addressdetails=1&q=campus+universit%C3%A0+cesena&format=json&limit=1"
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
    }

    private fun isOnline(): Boolean {
        return false
    }
}

@Composable
fun SnackBarConnectivityComposable(
    snackbarHostState: SnackbarHostState,
    applicationContext: Context,
    showSnackBar: MutableState<Boolean>
) {
    LaunchedEffect(snackbarHostState) {
        val result = snackbarHostState.showSnackbar(
            message = "No Internet available",
            actionLabel = "Go to settings",
            duration = SnackbarDuration.Indefinite
        )
        when (result) {
            SnackbarResult.ActionPerformed -> {
                val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                if (intent.resolveActivity(applicationContext.packageManager) != null) {
                    applicationContext.startActivity(intent)
                }
            }
            SnackbarResult.Dismissed -> {
                showSnackBar.value = false
            }
        }
    }
}