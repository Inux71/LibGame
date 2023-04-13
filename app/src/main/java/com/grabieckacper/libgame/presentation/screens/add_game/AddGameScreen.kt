package com.grabieckacper.libgame.presentation.screens.add_game

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.grabieckacper.libgame.R
import com.grabieckacper.libgame.common.components.GameCard
import com.grabieckacper.libgame.common.components.SearchField
import com.grabieckacper.libgame.common.enums.Status

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGameScreen(
    viewModel: AddGameViewModel = hiltViewModel(),
    onNavigateToDashboard: () -> Unit
) {
    val state = viewModel.state
    val context = LocalContext.current

    var searchText by remember {
        mutableStateOf(TextFieldValue(""))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.add_game))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateToDashboard()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.go_back)
                        )
                    }
                }
            )
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

                        viewModel.filterGames(searchText.text)
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

                                viewModel.filterGames(searchText.text)
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

            if (state.value.isLoading) {
                item {
                    Text(text = stringResource(id = R.string.loading))
                }
            } else {
                items(state.value.games.size) { index ->
                    GameCard(
                        isUserGame = false,
                        gameImageUrl = state.value.games[index].thumbnail!!,
                        gameTitle = state.value.games[index].title!!,
                        gameGenre = state.value.games[index].genre!!,
                        currentStatus = Status.PLAYING,
                        onAddGame = {
                            if (viewModel.addGameToUser(state.value.games[index].id!!)) {
                                Toast.makeText(
                                    context,
                                    "Dodano grę!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                onNavigateToDashboard()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Gra jest już dodana!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        onRemoveGame = {},
                        onUpdateGame = {}
                    )
                }
            }
        }
    }
}
