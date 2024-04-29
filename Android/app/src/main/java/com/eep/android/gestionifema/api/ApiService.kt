package com.eep.android.gestionifema.api

import com.eep.android.gestionifema.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun loginUser(@Body user: User): Call<User>

    @POST("register")
    fun registerUser(@Body user: User): Call<User>

    @POST("logout")
    fun logoutUser(@Body user: User): Call<User>

    @POST("user")
    fun getUser(@Body user: User): Call<User>

    @POST("users")
    fun getUsers(@Body user: User): Call<User>

    @POST("update")
    fun updateUser(@Body user: User): Call<User>

    @POST("delete")
    fun deleteUser(@Body user: User): Call<User>

    @POST("deleteAll")
    fun deleteAllUsers(@Body user: User): Call<User>
}