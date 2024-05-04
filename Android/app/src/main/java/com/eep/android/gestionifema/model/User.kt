package com.eep.android.gestionifema.model

data class User(
    val id: Int,
    val nombre: String,
    val email: String,
    val password: String,
    val rol: String,
    val centroVisita: String
)