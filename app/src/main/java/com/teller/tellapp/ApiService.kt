package com.teller.tellapp

import com.teller.tellapp.data.Teller
import com.teller.tellapp.data.User
import com.teller.tellapp.network.EntityResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("tran/getTran?id=1")
    fun getUsers(): Call<EntityResponse<Teller>>

    @GET("tellers/findById?id=1")
    fun getTellerDetails(): Call<EntityResponse<Teller>>

    @POST("auth/admin/signin")
    fun login(@Body loginData: User): Call<EntityResponse<User>>


//    @GET("Customer/getCustomer?id=1")
//    fun getCustomer(): Call<EntityResponse<Customer>>
//
//    @POST("account/withdrawReq?id=1")
//    fun withdrawReq(@Body data: Withdraw): Call<Void>
//
//    @GET("Transaction/get?id=4")
//    fun getTran(): Call<EntityResponse<Transaction>>

}
