package com.eep.android.gestionifema.model

import androidx.compose.ui.semantics.Role


data class LoginRequest(
    val username: String,
    val password: String,
    val role: String
)

