package com.example.jetpackcompose.ui.theme

import android.content.res.Configuration
import android.inputmethodservice.Keyboard
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.Greeting

// Esercizio 3
@Composable
fun OrientationChange(status: Configuration) {

    when(status.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> Greeting(name = "Hello Landscape")
        Configuration.ORIENTATION_PORTRAIT -> Greeting(name = "Hello Portrait")
        else -> { }
    }
}

// Esercizio 4
@Composable
fun VerticalLayout(status: Configuration) {
    Column() {
        Greeting(name = "Hello Android")
        OrientationChange(status = status)
    }
}

@Composable
fun HorizontalLayout(status: Configuration) {
    Row() {
        Greeting(name = "Hello Android")
        OrientationChange(status = status)
    }
}

@Composable
fun BoxConstraintsLayout() {
    BoxWithConstraints() {
        val scope = this
        Column {
            if(scope.maxHeight >= 400.dp) {
                Greeting(name = "<= 400.dp")
            }

            Spacer(modifier = Modifier.size(15.dp))
            Text(text = "minH ${scope.minHeight} \n" +
                    "maxH ${scope.maxHeight} \n" +
                    "minW ${scope.minWidth} \n" +
                    "maxW ${scope.maxWidth} \n" )
        }
    }
}

// Esercizio 5
@Composable
fun Greetings(names: List<String>) {
    for(name in names) {
        Text(text = "Hello $name")
    }
}

@Composable
fun ListNames() {
    val names = listOf("A", "B", "C", "D")
    Column() {
        Greetings(names = names)
    }
}

// Esercizio 6
@Composable
fun scrollableList() {
    val size = 50

    LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        items(size) {
            Text(text = "Item n. $it",
                color = if(isSystemInDarkTheme()) Purple80 else Purple40,
                style = MaterialTheme.typography.titleMedium)
        }
    }
}
