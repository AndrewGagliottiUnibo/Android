package com.example.intent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                GoToActivity()
            }
        }
    }

    private fun showMap() {
        val geolocation = Uri.parse("geo:44.1391, 12.24315")
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = geolocation
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    @Composable
    fun GoToActivity() {

        //val context = LocalContext.current

        Button(
            onClick = {
                // Considero la activity e la sua destinazione finale
                //val intent = Intent(context, SettingsActivity::class.java)
                //context.startActivity(intent)
                showMap()
            },
            modifier = Modifier.requiredSize(150.dp, 50.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimaryContainer)
        ) {
            Text(text = "Go to settings")
        }
    }
}
