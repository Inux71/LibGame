package com.grabieckacper.libgame.presentation.screens.add_game

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.grabieckacper.libgame.model.Game
import com.grabieckacper.libgame.model.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class AddGameState(
    val games: List<Game> = emptyList()
)

@HiltViewModel
class AddGameViewModel @Inject constructor(
    private val _databaseRepository: DatabaseRepository
) : ViewModel() {
    private val _state = mutableStateOf(AddGameState())
    val state: State<AddGameState>
        get() = this._state

    private val _games = this._databaseRepository.games

    init {
        this._state.value = this._state.value.copy(games = this._games)
    }

    fun filterGames(searchText: String) {
        val filteredGames = this._games.filter { game ->
            game.title!!.contains(searchText, true)
        }

        this._state.value = this._state.value.copy(games = filteredGames)
    }

    fun addGameToUser(gameId: Long): Boolean {
        return this._databaseRepository.addGameToUser(gameId)
    }
}
