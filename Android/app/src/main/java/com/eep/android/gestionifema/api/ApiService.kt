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
    fun getUsers(): Call<List<User>>

    @GET("user/{id}")
    fun getUserById(@Query("id") id: Int): Call<User>

    @PUT("user/{id}")
    fun updateUserById(@Query("id") id: Int, @Body user: User): Call<User>

    @DELETE("user/{id}")
    fun deleteUserById(@Query("id") id: Int): Call<User>

    @POST("user")
    fun createUser(@Body user: User): Call<User>

    @POST(value ="login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<User>


}