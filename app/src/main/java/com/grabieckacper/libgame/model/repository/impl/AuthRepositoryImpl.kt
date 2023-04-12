package com.grabieckacper.libgame.model.repository.impl

import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.grabieckacper.libgame.model.repository.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl: AuthRepository {
    private val _providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build()
    )
    override val providers: List<AuthUI.IdpConfig>
        get() = this._providers

    private val _user = FirebaseAuth.getInstance().currentUser
    override val user: FirebaseUser?
        get() = this._user

    private val _auth = AuthUI.getInstance()
    override val auth: AuthUI
        get() = this._auth

    override suspend fun signOut(context: Context) {
        this._auth
            .signOut(context)
            .await()
    }
}
