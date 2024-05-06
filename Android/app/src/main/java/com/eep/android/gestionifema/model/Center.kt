package com.eep.android.gestionifema.model

data class Center(
    val id: Int,
    val nombreCentro: String,
    val paginaWeb: String,
    val type: String?,  // Asumiendo que type puede ser nulo, ajusta según tu modelo real
    var isExpanded: Boolean = false
)
