package com.eep.android.gestionifema.model

//import androidx.room.Entity
//import androidx.room.PrimaryKey
//
//@Entity(tableName = "users")
data class User(
    val id: Int,
    val nombre: String,
    val edad: Int,
    val email: String,
    val password: String,
    val rol: String,
//    val centers: List<Center>,
    val centroVisita: String?,
)
//data class User(
////    @PrimaryKey(autoGenerate = true)
//    val id: Int ,
//    val email: String,
//    val password: String?,
//    val nombre: String,
//    val edad: Int,
//    val rol: String,
//    val centroVisita: String?,
//    val centerId: Int
//)