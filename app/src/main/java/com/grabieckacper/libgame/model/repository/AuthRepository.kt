package com.grabieckacper.libgame.model.repository

import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val providers: List<IdpConfig>
    val user: FirebaseUser?
    val auth: AuthUI

    suspend fun signOut(context: Context)
}
