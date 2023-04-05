package com.example.room

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.room.data.ListItem
import com.example.room.ui.theme.RoomTheme
import com.example.room.viewModel.ItemsViewModel
import com.example.room.viewModel.ItemsViewModelFactory

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val itemsViewModel by viewModels<ItemsViewModel> {
            ItemsViewModelFactory(repository = (application as ShoppingListApplication).repository)
        }
        setContent {
            RoomTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(floatingActionButton = {
                        ExtendedFloatingActionButton(
                            onClick = { itemsViewModel.clearItems() },
                            icon = { Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete All Items") },
                            text = { Text(text = "Delete Items") },
                        )
                    }) { paddingValues ->
                        ShoppingListApp(
                            paddingValues = paddingValues,
                            itemsViewModel = itemsViewModel,
                            onAddItem = { itemsViewModel.addItem(it) },
                            onDeleteItem = { itemsViewModel.deleteItem(it) },
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShoppingListApp(
    paddingValues: PaddingValues,
    itemsViewModel: ItemsViewModel,
    onAddItem: (ListItem) -> Unit,
    onDeleteItem: (ListItem) -> Unit
) {
    val listItems: List<ListItem> by itemsViewModel.allItems.collectAsState(initial = listOf())

    var newItem by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.padding(paddingValues)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = newItem,
                onValueChange = { newItem = it },
                label = { Text("New Item") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
            )
            IconButton(onClick = {
                if (newItem.trim().isNotEmpty()) {
                    onAddItem(ListItem(newItem.trim()))
                    newItem = ""
                    keyboardController?.hide()
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "Add item"
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(listItems) { item ->
                ItemLayout(
                    item = item,
                    onTrashClicked = {onDeleteItem(item)}
                )
            }
        }
    }
}

@Composable
fun ItemLayout(
    item: ListItem,
    onTrashClicked: (ListItem) -> Unit
) {
    val context = LocalContext.current

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(vertical = 12.dp, horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = item.listItem,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1f)
            )

            IconButton(
                onClick = {
                    onTrashClicked(item)
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Item",
                    tint = Color.White
                )
            }
        }
    }
}