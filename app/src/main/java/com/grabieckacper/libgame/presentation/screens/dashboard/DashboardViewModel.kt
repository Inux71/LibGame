package com.grabieckacper.libgame.presentation.screens.dashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import com.grabieckacper.libgame.common.enums.Status
import com.grabieckacper.libgame.model.Game
import com.grabieckacper.libgame.model.repository.AuthRepository
import com.grabieckacper.libgame.model.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardState(
    val isLoading: Boolean = true,
    val playingGames: List<Game> = emptyList(),
    val playedGames: List<Game> = emptyList()
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val _authRepository: AuthRepository,
    private val _databaseRepository: DatabaseRepository
) : ViewModel() {
    private val _auth = this._authRepository.auth
    val auth: AuthUI
        get() = this._auth

    private val _state = mutableStateOf(DashboardState())
    val state: State<DashboardState>
        get() = this._state

    private val _userGames = mutableListOf<Game>()

    init {
        this.viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            _databaseRepository.fetchGames()

            while (_databaseRepository.games.isEmpty()) {
                delay(1)
            }

            _databaseRepository.fetchUserGames { userGames ->
                _userGames.clear()
                _userGames.addAll(userGames)

                val playingGames = _userGames.filter { userGame ->
                    userGame.status == Status.PLAYING
                }

                val playedGames = _userGames.filter { userGame ->
                    userGame.status == Status.PLAYED
                }

                _state.value = _state.value.copy(
                    isLoading = false,
                    playingGames = playingGames,
                    playedGames = playedGames
                )
            }
        }
    }

    fun getUser(): FirebaseUser? {
        return this._authRepository.getUser()
    }

    fun filterGames(searchText: String, status: Status) {
        val filteredGames = this._userGames.filter { game ->
            game.status == status && game.title!!.contains(searchText, true)
        }

        when (status) {
            Status.PLAYING -> {
                this._state.value = this._state.value.copy(playingGames = filteredGames)
            }

            Status.PLAYED -> {
                this._state.value = this._state.value.copy(playedGames = filteredGames)
            }
        }
    }
}
