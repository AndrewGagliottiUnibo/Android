package com.example.jetpackcompose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Il padre è il tema della applicazione
            JetpackComposeTheme {
                
                val configuration = LocalConfiguration.current
                // A surface container using the 'background' color from the theme
                Surface(
                    // Esercizio 2
                        modifier = Modifier.fillMaxSize(),
                    //modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp/2,
                        //LocalConfiguration.current.screenHeightDp.dp),
                    // Esercizio 1
                    color = Purple80
                ) {
                    //Greeting("Android")
                    //OrientationChange(configuration)
                    //VerticalLayout(status = configuration)
                    //HorizontalLayout(status = configuration)
                    //BoxConstraintsLayout()
                    //ListNames()
                    //ScrollableList()
                    scaffoldFunc()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeTheme {
        Greeting("Android")
    }
}