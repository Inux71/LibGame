package com.grabieckacper.libgame.model.repository.impl

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.grabieckacper.libgame.config.AppConfig
import com.grabieckacper.libgame.model.Game
import com.grabieckacper.libgame.model.repository.DatabaseRepository

class DatabaseRepositoryImpl : DatabaseRepository {
    private val _database = Firebase.database(AppConfig.DATABASE_URL)
    private val _gamesRef = this._database.getReference("games")

    override fun fetchGames(games: MutableList<Game>, updateStateCallback: () -> Unit) {
        this._gamesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val g = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue(Game::class.java)!!
                }

                games.clear()
                games.addAll(g)

                updateStateCallback()
            }
        })
    }
}
