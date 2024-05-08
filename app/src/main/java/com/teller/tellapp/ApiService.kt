package com.teller.tellapp

import com.google.gson.JsonObject
import com.teller.tellapp.data.Teller
import com.teller.tellapp.data.User
import com.teller.tellapp.data.Withdraw
import com.teller.tellapp.network.EntityResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("tellers/findById?id=1")
    fun getTellerDetails(): Call<EntityResponse<Teller>>

    @POST("auth/admin/signin")
    fun login(@Body loginData: User): Call<EntityResponse<User>>

//    @POST("account/approve")
//    fun approve(@Query("tellerid") tellerId: Long, @Body customerTransaction: Any): Call<EntityResponse<CustomerTransaction>>

    @POST("account/approve?tellerid=1")
    fun approve( @Body withdraw: JsonObject): Call<EntityResponse<Withdraw>>


}