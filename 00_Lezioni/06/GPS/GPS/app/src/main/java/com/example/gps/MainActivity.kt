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

    // Avviso android che servono determinanti elementi ma che li inizializzo poi
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // richiamata ogni volta che si ha un aggiornamento della posizione al fine di modificare
    // l'interfaccia
    private lateinit var locationCallback: LocationCallback

    // come prima, per la richiesta effettiva
    private lateinit var locationRequest: LocationRequest

    // gestione permessi: quando faremo una richiesta questa sarà un intent che ritorna un
    // determinato risultato
    private lateinit var locationPermissionRequest: ActivityResultLauncher<String>

    // controllo booleano per vedere se l'utente ha necessità di tenere traccia della posizione
    private var requestingLocationUpdates = false

    // stato mutabile, modificabile poi
    private var showSnackBar = mutableStateOf(false)
    private var showAlertDialog = mutableStateOf(false)
    private val location = mutableStateOf(LocationDetails(0.toDouble(), 0.toDouble()))

    // richiesta per la localizzazione
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // prende una activity già creata come input
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // varie costanti in base alla accuratezza desiderata
        locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).apply {
                // la granularità in base ai permessi dell'utente, in questo modo si interagisce in
                // modo automatico
                setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            }.build()

        // non operazione di interfaccia ma che deve essere comunque svolta internamente
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                // .value per impostare i valori di uno stato mutabile, in questo caso si lavora
                // con un array di stati mutabili
                location.value = LocationDetails(
                    p0.locations.last().latitude,
                    p0.locations.last().longitude
                )

                // ho ottenuto la posizione e non la devo più richiedere
                stopLocationUpdates()
                requestingLocationUpdates = false
            }
        }

        // siccome richiedo dei permessi all'utente, devo verificare che siano stati concessi
        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                // se è concesso allora controllo la posizione
                startLocationUpdates()
            } else {
                // se il permesso non è concesso dovrò rimostrare poi la finestra di dialogo
                showSnackBar.value = true
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

                    // Snackbar e alert dialog
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
        // siccome ci può essere una fase in cui la posizione venga richiesta senza essere passati
        // per la fase precedente, devo controllare da dove viene chiamato e se sono stati concessi
        // o meno i permessi
        requestingLocationUpdates = true
        val permission = Manifest.permission.ACCESS_COARSE_LOCATION

        when {
            //permission already granted - l'untente ha fornito i permessi necessari quindi il
            // il provider deve far partire gli aggiornamenti sulla posizione
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                val gpsEnabled = checkGPS()
                if (gpsEnabled) {
                    // allora se il GPS è on e ho i permessi posso controllare la posizione passando
                    // le necessarie variabili e richiamando il main
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

            // default case
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