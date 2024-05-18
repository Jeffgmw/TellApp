package com.teller.tellapp.network

import com.google.gson.JsonObject
import com.teller.tellapp.data.ChangePassword
import com.teller.tellapp.data.Customer
import com.teller.tellapp.data.GenLedger
import com.teller.tellapp.data.Referral
import com.teller.tellapp.data.Teller
import com.teller.tellapp.data.Trans
import com.teller.tellapp.data.User
import com.teller.tellapp.network.EntityResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("tellers/findById?id=1")
    fun getTellerDetails(): Call<EntityResponse<Teller>>

    @POST("auth/admin/signin")
    fun login(@Body loginData: User): Call<EntityResponse<User>>

    @POST("account/approve?tellerid=1")
    fun approve( @Body trans: JsonObject): Call<EntityResponse<Trans>>

    @GET("account/findByAccno")
    fun searchAccount(@Query("accno") account: Long): Call<EntityResponse<Customer>>

    @GET("gl/findById")
    fun getTellerGl(@Query("id") id: Long): Call<EntityResponse<GenLedger>>

    @POST("auth/reset-password")
    fun changePassword(@Body changePassword: ChangePassword): Call<EntityResponse<Any>>

    @POST("referral/replenish")
    fun replenish(@Query("tellerId") tellerId: Long, @Body referral: Referral): Call<EntityResponse<Any>>

    @POST("referral/retrench")
    fun retrench(@Query("tellerId") tellerId: Long, @Body referral: Referral): Call<EntityResponse<Any>>

}