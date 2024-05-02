package com.teller.tellapp

import com.teller.tellapp.data.CustomerTransaction
import com.teller.tellapp.data.Teller
import com.teller.tellapp.data.User
import com.teller.tellapp.network.EntityResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {

    @GET("tellers/findById?id=1")
    fun getTellerDetails(): Call<EntityResponse<Teller>>

    @POST("auth/admin/signin")
    fun login(@Body loginData: User): Call<EntityResponse<User>>

//    @PUT("account/approve?id=1")
//    fun approve(@Body customerTransaction: Any):Call<EntityResponse<CustomerTransaction>>

    @PUT("account/approve")
    fun approve(@Query("id") id: Long, @Body customerTransaction: Any): Call<EntityResponse<CustomerTransaction>>



}
