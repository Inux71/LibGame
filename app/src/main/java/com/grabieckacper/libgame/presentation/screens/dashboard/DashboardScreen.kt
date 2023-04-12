package com.grabieckacper.libgame.presentation.screens.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.grabieckacper.libgame.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedView by remember {
        mutableStateOf(0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "User")
                },
                actions = {
                    IconButton(
                        onClick = {
                            expanded = true
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Otwórz ustawienia"
                            )
                        }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        },
                        content = {
                            DropdownMenuItem(
                                text = {
                                    Text(text = "Wyloguj się")
                                },
                                onClick = {
                                    expanded = false
                                }
                            )
                        }
                    )
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
                            contentDescription = "Do zagrania"
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
                            contentDescription = "Zagrane"
                        )
                    }
                )
            })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                },
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Dodaj grę"
                    )
                }
            )
        },
        content = { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding))
        }
    )
}
