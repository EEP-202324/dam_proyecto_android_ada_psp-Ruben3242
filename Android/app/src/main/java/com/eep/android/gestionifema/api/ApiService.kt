package com.eep.android.gestionifema.api

import com.eep.android.gestionifema.model.LoginRequest
import com.eep.android.gestionifema.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    companion object Factory{
        private const val BASE_URL = "http://localhost:8080"
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }


    @GET("user")
    fun listaUsuarios(): Call<List<User>>

    @POST(value ="login")
    fun loginUser(@Body loginRequest: LoginRequest):
            Call<LoginRequest>


    @DELETE("user/{id}")
    fun registerUser(): Call<User>

    @POST("logout")
    fun logoutUser(@Body user: User): Call<User>

    @POST("user")
    fun getUser(@Body user: User): Call<User>

    @POST("users")
    fun getUsers(@Body user: User): Call<User>

    @PUT("")
    fun updateUser(@Body user: User): Call<User>

    @POST("delete")
    fun deleteUser(@Body user: User): Call<User>

}