package com.example.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

// Contiene le parti dei singoli screen
sealed class NavigationScreen(val name: String) {
    object Home : NavigationScreen("Home")
    object Second : NavigationScreen("Second")
    object Third : NavigationScreen("Third")
}

// L'interfaccia vera e propria
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationApp(navController: NavHostController = rememberNavController()) {

    // Tramite il mutable state
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: NavigationScreen.Home.name

    Scaffold(
        topBar = {
            NavigationAppBar(
                currentScreen = currentScreen,
                navigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }) { padding ->
        NavigationGraph(navController = navController, paddingValues = padding)
    }
}

// Navigazione
@Composable
fun NavigationGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    // Tutte le destinazioni possibili sono nel navHost come funzioni composable
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.Home.name,
        modifier = Modifier.padding(
            paddingValues
        )
    ) {
        composable(route = NavigationScreen.Home.name) {
            HomeScreen(onNextButton = { navController.navigate(NavigationScreen.Second.name) })
        }
        composable(route = NavigationScreen.Second.name) {
            SecondScreen(
                onNextButton = { navController.navigate(NavigationScreen.Third.name) },
                onCancelButton = { navigateHome(navController) })
        }
        composable(route = NavigationScreen.Third.name) {
            ThirdScreen(onCancelButton = { navigateHome(navController) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationAppBar(
    modifier: Modifier = Modifier,
    currentScreen: String,
    navigateBack: Boolean,
    navigateUp: () -> Unit
) {
    TopAppBar(
        title = { Text(text = currentScreen) },
        modifier = modifier,
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        navigationIcon = {
            if (navigateBack) {
                IconButton(onClick = { navigateUp() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back arrow")
                }
            }
        })
}

fun navigateHome(navController: NavHostController) {
    // Rimuovo tutte le altre destinazioni e torno alla home page per poi visualizzarla, ripulendo
    // lo stack delle pagine tranne quella della home page stessa
    navController.popBackStack(NavigationScreen.Home.name, inclusive = false)

}
