package com.grabieckacper.libgame.model.repository.impl

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.grabieckacper.libgame.common.enums.Status
import com.grabieckacper.libgame.config.AppConfig
import com.grabieckacper.libgame.model.Game
import com.grabieckacper.libgame.model.UserGame
import com.grabieckacper.libgame.model.repository.DatabaseRepository

class DatabaseRepositoryImpl(private val _userId: String) : DatabaseRepository {
    private val _database = Firebase.database(AppConfig.DATABASE_URL)
    private val _gamesRef = this._database.getReference("games")
    private val _userGamesRef = this._database.getReference("user-games")

    private val _games = mutableListOf<Game>()
    override val games: List<Game>
        get() = this._games

    private val _userGames = mutableListOf<UserGame>()

    private fun getGameById(gameId: Long): Game {
        return this._games.first { game ->
            game.id == gameId
        }
    }

    override fun fetchGames() {
        this._gamesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val g = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue(Game::class.java)!!
                }

                _games.clear()
                _games.addAll(g)
            }
        })
    }

    override fun fetchUserGames(updateStateCallback: (userGames: List<Game>) -> Unit) {
        this._userGamesRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val ug = snapshot
                    .child(_userId)
                    .children
                    .map { dataSnapshot ->
                        dataSnapshot.getValue(UserGame::class.java)!!
                    }

                _userGames.clear()
                _userGames.addAll(ug)

                updateStateCallback(ug.map { userGame ->
                    val game = getGameById(userGame.gameId!!)
                    game.status = userGame.status
                    game
                })
            }
        })
    }

    override fun addGameToUser(gameId: Long): Boolean {
        val userGame = UserGame(gameId, Status.PLAYING)

        if (this._userGames.contains(userGame)) {
            return false
        }

        this._userGamesRef
            .child(this._userId)
            .child(gameId.toString())
            .setValue(userGame)

        return true
    }

    override fun removeGameFromUser(gameId: Long) {
        this._userGamesRef
            .child(this._userId)
            .child(gameId.toString())
            .removeValue()
    }

    override fun updateGameStatus(gameId: Long, status: Status) {
        val userGame = UserGame(gameId, status)

        this._userGamesRef
            .child(this._userId)
            .child(gameId.toString())
            .setValue(userGame)
    }
}
