package com.grabieckacper.libgame.model.repository

import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig

interface AuthRepository {
    val providers: List<IdpConfig>
    val auth: AuthUI
}
