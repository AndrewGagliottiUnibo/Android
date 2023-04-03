package com.example.jetpackcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

// Esercizio 7
@Composable
fun bottomAppBarFunc() {
    BottomAppBar() {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons .Filled.Menu, contentDescription = "Menu")
        }

        Spacer(modifier = Modifier.weight(1f, true))

        IconButton(onClick = { /*TODO*/ }) {
            Icon(painter = painterResource(id = R.drawable.shopping_cart_24), contentDescription = "Shopping cart")
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons .Filled.Favorite, contentDescription = "Favourite")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun scaffoldFunc() {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                   Icon(Icons.Filled.Add, contentDescription = "Add item")
            }
        },
        bottomBar = { bottomAppBarFunc() }
    ) {
        listItems(padding = it)
    }
}

@Composable
fun listItems(padding: PaddingValues) {
    val size = 50

    LazyColumn(modifier = Modifier.padding(padding), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        items(size) {
            listItemsImage(it)
        }
    }
}

@Composable
fun listItem(index: Int) {
    // Di default prende la grandezza del testo, quindi scrolli interagendo con il testo
    Text(text = "Item n. $index",
        color = MaterialTheme.colorScheme.onSecondary,
        style = MaterialTheme.typography.titleMedium)
}

@Composable
fun listItemsImage(index: Int) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxWidth()
            .padding(5.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = R.drawable.android_24), contentDescription = "android pic", colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSecondary))
        Spacer(modifier = Modifier.width(15.dp))
        listItem(index = index)
    }
}