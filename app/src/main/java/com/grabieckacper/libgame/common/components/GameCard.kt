package com.grabieckacper.libgame.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.grabieckacper.libgame.R
import com.grabieckacper.libgame.common.enums.Status

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameCard(
    isUserGame: Boolean,
    gameImageUrl: String,
    gameTitle: String,
    gameGenre: String,
    currentStatus: Status,
    onAddGame: () -> Unit,
    onRemoveGame: () -> Unit,
    onUpdateGame: () -> Unit
) {
    val statusList = enumValues<Status>()

    var selectedStatus by remember {
        mutableStateOf(currentStatus)
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    Card(modifier = Modifier
        .fillMaxWidth(0.9f)
        .aspectRatio(1.5f)
        .padding(0.dp, 10.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = gameImageUrl,
                contentDescription = "Grafika",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = if (isUserGame) {
                    Arrangement.SpaceBetween
                } else {
                    Arrangement.Bottom
                },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isUserGame) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            onRemoveGame()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = stringResource(id = R.string.remove)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = if (isUserGame) {
                            Modifier
                                .padding(10.dp, 0.dp, 0.dp, 0.dp)
                                .fillMaxWidth(0.6f)
                        } else {
                            Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                        },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = gameTitle,
                            fontWeight = FontWeight.Bold,
                            overflow =  TextOverflow.Ellipsis,
                            maxLines = 1
                        )

                        Text(
                            text = gameGenre,
                            fontSize = 10.sp
                        )
                    }

                    if (isUserGame) {
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = {
                                expanded = !expanded
                            }
                        ) {
                            TextField(
                                value = selectedStatus.name,
                                onValueChange = {},
                                modifier = Modifier.menuAnchor(),
                                readOnly = true,
                                textStyle = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                },
                                singleLine = true,
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                )
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = {
                                    expanded = false
                                }
                            ) {
                                statusList.forEach { status ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(text = status.name)
                                        },
                                        onClick = {
                                            selectedStatus = status
                                            expanded = false
                                            onUpdateGame()
                                        }
                                    )
                                }
                            }
                        }
                    } else {
                        IconButton(onClick = {
                            onAddGame()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(id = R.string.add_game)
                            )
                        }
                    }
                }
            }
        }
    }
}
