package com.eep.android.gestionifema.model

data class Center(
    val id: Int,
    val nombreCentro: String,
    val paginaWeb: String,
    val type: String?,
    val address: String,
    val telefono: String,
    val descripcion: String
)
