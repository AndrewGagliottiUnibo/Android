package com.example.sharedpref

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.sharedpref.ui.theme.SharedPrefTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val THEME_KEY = "THEME_KEY"
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val theme = sharedPref.getString(THEME_KEY, getString(R.string.dark_theme))

        setContent {
            SharedPrefTheme(darkTheme = theme=="Dark") {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    //val radioOptions = listOf("Light", "Dark")

                    val radioOptions = listOf(getString(R.string.light_theme), getString(R.string.dark_theme))
                    val (selectedOption, onOptionSelected) = remember { mutableStateOf(theme) }

                    Column(Modifier.selectableGroup()) {
                        radioOptions.forEach { text ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .selectable(
                                        selected = (text == selectedOption),
                                        onClick = {
                                            onOptionSelected(text)
                                            with(sharedPref.edit()) {
                                                putString(THEME_KEY, text)
                                                apply()
                                            }
                                        },
                                        role = Role.RadioButton
                                    )
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = (text == selectedOption),
                                    onClick = null // null recommended for accessibility with screenreaders
                                )
                                Text(
                                    text = text,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
