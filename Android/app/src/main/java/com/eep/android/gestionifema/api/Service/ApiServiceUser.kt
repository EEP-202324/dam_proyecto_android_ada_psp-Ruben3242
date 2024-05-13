package com.eep.android.gestionifema.api.Service

import com.eep.android.gestionifema.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceUser {
    @GET
    suspend fun getUsers(): Response<List<User>>

    @GET("user/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<User>//FUNCIONA

    @PUT("user/{id}")
    suspend fun updateUserById(@Path("id") id: Int, @Body user: User): Response<User>

    @DELETE("user/{id}")
    suspend fun deleteUserById(@Query("id") id: Int): Response<User>

    @POST("user")
    suspend fun createUser(@Body user: User): Response<User>
}