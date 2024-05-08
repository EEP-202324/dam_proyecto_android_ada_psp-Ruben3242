package com.eep.android.gestionifema.model

//import androidx.room.Entity
//import androidx.room.PrimaryKey
//
//@Entity(tableName = "users")
data class User(
//    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val email: String,
    val password: String,
    val nombre: String,
    val edad: Int,
    val rol: String,
    val centroVisita: String?
)
