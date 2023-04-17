package com.example.volley

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
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
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.volley.ui.theme.VolleyTheme
import org.json.JSONObject

class MainActivity : ComponentActivity() {

    private lateinit var connectivityManager : ConnectivityManager
    private var queue: RequestQueue? = null
    private val TAG = "OSM_REQUEST"

    private var requestingData = false

    private var showConnectivitySnackBar = mutableStateOf(false)
    private val place = mutableStateOf("")

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                if (requestingData) {
                    sendRequest()
                    showConnectivitySnackBar.value = false
                }
            }

            override fun onLost(network: Network) {
                showConnectivitySnackBar.value = true
            }
        }

        setContent {
            VolleyTheme {
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
                                    requestingData = true
                                    connectivityManager.registerDefaultNetworkCallback(
                                        networkCallback
                                    )
                                    if (isOnline()) {
                                        sendRequest()
                                    } else {
                                        showConnectivitySnackBar.value = true
                                    }
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
        queue = Volley.newRequestQueue(this)

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val first: JSONObject = response.get(0) as JSONObject
                place.value = first.get("display_name").toString()
                connectivityManager.unregisterNetworkCallback(networkCallback)
                requestingData = false
            },
            { error ->
                Log.e("MAINACTIVITY-SENDREQUEST", error.toString())
            }
        )

        jsonArrayRequest.tag = TAG
        queue?.add(jsonArrayRequest)
    }

    override fun onStop() {
        super.onStop()
        queue?.cancelAll(TAG)
        if (requestingData)
            (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .unregisterNetworkCallback(networkCallback)
    }

    override fun onStart() {
        super.onStart()
        if (requestingData)
            (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .registerDefaultNetworkCallback(networkCallback)
    }

    private fun isOnline(): Boolean {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true ||
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
        ) {
            return true
        }
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