package com.eep.android.gestionifema.api

import com.eep.android.gestionifema.model.Center
import com.eep.android.gestionifema.model.LoginRequest
import com.eep.android.gestionifema.model.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.lang.reflect.Type


interface ApiService {

    @GET
    fun getUsers(): Response<List<User>>

    @GET("user/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<User>//FUNCIONA

    @PUT("user/{id}")
    suspend fun updateUserById(@Path("id") id: Int, @Body user: User): Response<User>

    @DELETE("user/{id}")
    fun deleteUserById(@Query("id") id: Int): Response<User>

    @POST("user")
    fun createUser(@Body user: User): Response<User>

    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): Response<User>
//////////////////////////////////////////////////////////////////////
    @GET("centers")
    suspend fun getCenters(): Response<List<Center>>

    @GET("centers/{id}")
    fun getCenterById(@Path("id") id: Int): Response<Center>



}
private const val BASE_URL =
    "http://10.0.2.2:8080/"


val gson = GsonBuilder()
    .registerTypeAdapter(User::class.java, object : JsonDeserializer<User> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): User? {
            // Verificar si el elemento JSON no es un objeto
            if (!json.isJsonObject) {
                throw JsonParseException("Expected JSON object")
            }

            // Deserialización de un objeto JSON
            return Gson().fromJson(json, User::class.java)
        }
    })
    .create()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .build()

object ApiClient {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}