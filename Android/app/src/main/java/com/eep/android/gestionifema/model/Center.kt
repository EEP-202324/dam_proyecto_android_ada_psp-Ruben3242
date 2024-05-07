package com.eep.android.gestionifema.model

data class Center(
    val id: Int,
    val nombreCentro: String,
    val paginaWeb: String,
    val type: String?,  // Asumiendo que type puede ser nulo, ajusta según tu modelo real

    val descripcion: String
)
//data class Center(
//    val id: Int,
//    val name: String,
//    val imageUrl: String,
//    val webUrl: String,
//    val description: String,
//    val type: String  // 'Universidad' o 'FormacionProfesional'
//)