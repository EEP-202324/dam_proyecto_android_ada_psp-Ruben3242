package com.eep.android.gestionifema.api.Service

import com.eep.android.gestionifema.model.LoginRequest
import com.eep.android.gestionifema.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceLogin {
    @POST("login")
    suspend fun loginUser(@Body request: LoginRequest): Response<User>
}