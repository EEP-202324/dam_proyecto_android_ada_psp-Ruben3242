package com.eep.android.gestionifema.model

data class Center(
    val id: Int,
    val name: String,
    val web: String,

    val type: String,
    val address: String,
    val phone: String,
    val descr: String
)
