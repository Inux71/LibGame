package com.grabieckacper.libgame.model.repository

import com.grabieckacper.libgame.model.Game

interface DatabaseRepository {
    fun fetchGames(games: MutableList<Game>, updateStateCallback: () -> Unit)
}
