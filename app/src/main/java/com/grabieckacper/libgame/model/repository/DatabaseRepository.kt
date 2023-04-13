package com.grabieckacper.libgame.model.repository

import com.grabieckacper.libgame.model.Game

interface DatabaseRepository {
    val games: List<Game>
    fun fetchGames()
    fun fetchUserGames(updateStateCallback: (userGames: List<Game>) -> Unit)
    fun addGameToUser(gameId: Long): Boolean
    fun removeGameFromUser(gameId: Long)
}
