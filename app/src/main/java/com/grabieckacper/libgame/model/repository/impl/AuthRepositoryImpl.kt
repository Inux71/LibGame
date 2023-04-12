package com.grabieckacper.libgame.model.repository.impl

import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.grabieckacper.libgame.model.repository.AuthRepository

class AuthRepositoryImpl: AuthRepository {
    private val _providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build()
    )
    override val providers: List<AuthUI.IdpConfig>
        get() = this._providers

    private val _auth = AuthUI.getInstance()
    override val auth: AuthUI
        get() = this._auth

    private val _firebaseAuth = FirebaseAuth.getInstance()

    override fun getUser(): FirebaseUser? {
        return this._firebaseAuth.currentUser
    }
}
