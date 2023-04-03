package com.example.calculateviewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calculateviewmodel.ui.theme.CalculateViewModelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculateViewModelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculateApp()
                }
            }
        }
    }
}

@Composable
fun CalculateApp() {
    val navController = rememberNavController()
    // richiamando questo composable ci viene restituito il viewmodel desiderato
    val viewModel: CalculateViewModel = viewModel()

    NavHost(navController = navController, startDestination = "Home") {
        composable("Home") {
            HomeScreen(navController, viewModel)
        }
        composable("Result") {
            ResultScreen(navController, viewModel)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController, viewModel: CalculateViewModel) {

    var number by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = { Text(text = "Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.padding())

        Button(onClick = {
            val value = number.toIntOrNull() ?: return@Button
            viewModel.sum(value)
            navController.navigate("Result")
        }) {
            Text(text = "Calculate")
        }
    }
}

@Composable
fun ResultScreen(navController: NavController, viewModel: CalculateViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Sum: ${viewModel.value}")

        Spacer(modifier = Modifier.padding())

        Button(onClick = {
            navController.navigate("Home")
        }) {
            Text(text = "Calculate")
        }
    }


}
