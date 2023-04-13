package com.grabieckacper.libgame.model.repository

import com.grabieckacper.libgame.model.Game

interface DatabaseRepository {
    fun fetchGames(updateStateCallback: (games: List<Game>) -> Unit)
    fun fetchUserGames()
    fun addGameToUser(gameId: Long): Boolean
}
