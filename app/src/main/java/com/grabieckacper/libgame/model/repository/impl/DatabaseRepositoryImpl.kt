package com.grabieckacper.libgame.model.repository.impl

import android.util.Log
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

    private val _userGames = mutableListOf<UserGame>()

    override fun fetchGames(updateStateCallback: (games: List<Game>) -> Unit) {
        this._gamesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val g = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue(Game::class.java)!!
                }

                updateStateCallback(g)
            }
        })
    }

    override fun fetchUserGames() {
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
            .push()
            .setValue(userGame)

        return true
    }
}
