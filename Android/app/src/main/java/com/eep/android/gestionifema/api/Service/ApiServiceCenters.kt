package com.eep.android.gestionifema.api.Service

import com.eep.android.gestionifema.model.Center
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceCenters {
    @GET("centers")
    suspend fun getCenters(): Response<List<Center>>

    @GET("centers/{id}")
    suspend fun getCenterById(@Path("id") id: Int): Response<Center>

    @PUT("centers/{id}")
    suspend fun updateCenterById(@Path("id") centerId: Int,@Body updatedCenter: Center): Response<Center>

    @POST("centers")
    suspend fun createCenter(@Body center: Center): Response<Center>

    @DELETE("centers/{id}")
    suspend fun deleteCenterById(@Path("id") centerId: Int): Response<Unit>
}