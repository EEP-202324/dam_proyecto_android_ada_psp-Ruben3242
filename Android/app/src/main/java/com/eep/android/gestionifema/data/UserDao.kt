package com.eep.android.gestionifema.data


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.eep.android.gestionifema.model.User
import retrofit2.http.DELETE

@Dao
interface UserDao {

    @Update
    suspend fun updateUser(user: User)

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("UPDATE users SET nombre = :nombre, edad = :edad, centroVisita = :centroVisita WHERE id = :id")
    suspend fun updateUser(id: Int, nombre: String, edad: Int, centroVisita: String)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

    @DELETE
    suspend fun deleteUserbyId(id: Int)

}
