package com.eep.android.gestionifema.model

data class LoginRequest(
    val username: String,
    val password: String,
    val role: String
)

