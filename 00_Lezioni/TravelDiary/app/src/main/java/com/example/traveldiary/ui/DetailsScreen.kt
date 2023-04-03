package com.example.traveldiary.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.traveldiary.R
import com.example.traveldiary.data.Place
import com.example.traveldiary.viewModel.PlacesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(placesViewModel: PlacesViewModel) {
    val context = LocalContext.current
    val selectedPlace = placesViewModel.placeSelected
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { shareDetails(context, selectedPlace) }) {
                Icon(Icons.Filled.Share, contentDescription = stringResource(id = R.string.add_travel))
            }
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .padding(10.dp)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_android_24),
                contentDescription = "image taken",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSecondaryContainer)
            )

            Spacer(modifier = Modifier.size(15.dp))

            Text(
                text = selectedPlace?.placeName?:stringResource(id = R.string.place_title),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.size(15.dp))

            Text(
                text = selectedPlace?.travelDate?:stringResource(id = R.string.place_date),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.size(15.dp))

            Text(
                text = selectedPlace?.placeDescription?:stringResource(id = R.string.place_description),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodyMedium
            )

        }
    }
}

private fun shareDetails(context: Context, place: Place?){
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, place?.placeName?:"No place")
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}