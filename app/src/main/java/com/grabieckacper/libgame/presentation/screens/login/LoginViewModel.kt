package com.grabieckacper.libgame.presentation.screens.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.grabieckacper.libgame.R
import com.grabieckacper.libgame.model.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val _authRepository: AuthRepository
) : ViewModel() {
    private val _signInIntent = this._authRepository
        .auth
        .createSignInIntentBuilder()
        .setAvailableProviders(this._authRepository.providers)
        .setIsSmartLockEnabled(false)
        .setTheme(R.style.Theme_LibGame)
        .build()
    val signInIntent: Intent
        get() = this._signInIntent
}
