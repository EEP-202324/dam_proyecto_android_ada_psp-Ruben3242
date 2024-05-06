package com.eep.android.gestionifema.model

data class LoginRequest(
    val email: String,
    val password: String,
    val role: String
)

