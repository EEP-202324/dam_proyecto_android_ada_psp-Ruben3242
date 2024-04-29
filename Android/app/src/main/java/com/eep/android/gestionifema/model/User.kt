package com.eep.android.gestionifema.model

data class User(
    val id: Int,
    val nombre: String,
    val email: String,
    val password: String,
    val rol: String
) {
    override fun toString(): String {
        return "User(id=$id, nombre='$nombre', email='$email', password='$password', rol='$rol')"
    }
}