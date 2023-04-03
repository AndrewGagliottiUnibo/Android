package com.example.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Per ogni shermata creo un composable diretto: ogni schermata in input ha dei modificatori per
// aiutare il file a gestire le varie schermate
@Composable
fun HomeScreen(modifier: Modifier = Modifier, onNextButton: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home Screen", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = { onNextButton() }) {
            Text(text = "next")
        }
    }
}

@Composable
fun SecondScreen(
    modifier: Modifier = Modifier,
    onCancelButton: () -> Unit,
    onNextButton: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Second", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(10.dp))

        Row() {
            Button(onClick = { onCancelButton() }) {
                Text(text = "cancel")
            }

            Spacer(modifier = Modifier.width(5.dp))

            Button(onClick = { onNextButton() }) {
                Text(text = "next")
            }
        }
    }
}

@Composable
fun ThirdScreen(modifier: Modifier = Modifier, onCancelButton: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Third", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = { onCancelButton() }) {
            Text(text = "cancel")
        }
    }
}