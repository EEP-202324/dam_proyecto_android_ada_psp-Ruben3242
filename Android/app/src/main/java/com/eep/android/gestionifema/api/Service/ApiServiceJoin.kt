package com.eep.android.gestionifema.api.Service

import com.eep.android.gestionifema.model.Center
import com.eep.android.gestionifema.model.User
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServiceJoin {
    @GET("users/{userId}/centers")
    suspend fun getUserCenters(@Path("userId") userId: Int): Response<List<Center>>

    @POST("{userId}/centers/{centerId}")
    suspend fun addUserCenter(@Path("userId") userId: Int, @Path("centerId") centerId: Int): Response<User>

    @DELETE("{userId}/centers/{centerId}")
    suspend fun removeUserCenter(@Path("userId") userId: Int, @Path("centerId") centerId: Int): Response<User>
}