package com.grabieckacper.libgame.model.repository

import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val providers: List<IdpConfig>
    val auth: AuthUI

    fun getUser(): FirebaseUser?
}
