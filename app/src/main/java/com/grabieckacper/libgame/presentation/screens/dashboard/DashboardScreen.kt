package com.grabieckacper.libgame.presentation.screens.dashboard

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.grabieckacper.libgame.R
import com.grabieckacper.libgame.common.components.SearchField
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToAddGame: () -> Unit
) {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    val user = viewModel.getUser()

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedView by remember {
        mutableStateOf(0)
    }

    var searchText by remember {
        mutableStateOf(TextFieldValue(""))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = user?.displayName ?: "")
                },
                actions = {
                    IconButton(onClick = {
                        expanded = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(id = R.string.open_settings)
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(id = R.string.logout))
                            },
                            onClick = {
                                coroutine.launch {
                                    expanded = false

                                    viewModel.auth
                                        .signOut(context)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                context,
                                                "Pomyślnie wylogowano!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }.addOnFailureListener {
                                            Toast.makeText(
                                                context,
                                                "Podczas wylogowania wystąpił błąd!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }.await()

                                    onNavigateToLogin()
                                }
                            }
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(content = {
                NavigationBarItem(
                    selected = selectedView == 0,
                    onClick = {
                        selectedView = 0
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_videogame_asset_off_24),
                            contentDescription = stringResource(id = R.string.playing)
                        )
                    }
                )

                NavigationBarItem(
                    selected = selectedView == 1,
                    onClick = {
                        selectedView = 1
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_videogame_asset_24),
                            contentDescription = stringResource(id = R.string.played)
                        )
                    }
                )
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onNavigateToAddGame()
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_game)
                )
            }
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                SearchField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                    },
                    modifier = Modifier.fillMaxWidth(0.75f),
                    placeholder = {
                        Text(text = stringResource(id = R.string.search))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search)
                        )
                    },
                    trailingIcon = {
                        if (searchText.text.isNotEmpty()) {
                            IconButton(onClick = {
                                searchText = TextFieldValue("")
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = stringResource(id = R.string.clear)
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}
