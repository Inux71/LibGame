package com.grabieckacper.libgame.presentation.screens.dashboard

import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.grabieckacper.libgame.model.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val _authRepository: AuthRepository
) : ViewModel() {
    private val _auth = this._authRepository.auth
    val auth: AuthUI
        get() = this._auth

    fun getUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }
}
