package com.eep.android.gestionifema.model

data class UserUpdateInfo(
    val nombre: String,
    val email: String,
    val rol: String,
    val centroVisita: String,
    val edad: Int
)
