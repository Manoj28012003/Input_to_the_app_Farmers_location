package com.example.inputtotheappfarmerslocation.model


import com.ymts0579.model.model.DefaultResponse
import com.ymts0579.model.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("users.php")
    fun register(
        @Field("name") name: String,
        @Field("num") num: String,
        @Field("email") email: String,
        @Field("address") address: String,
        @Field("city") city: String,
        @Field("pass") pass: String,
        @Field("type") type: String,
        @Field("status") status: String,
        @Field("path") path: String,
        @Field("condition") condition: String,
    ): Call<DefaultResponse>


    @FormUrlEncoded
    @POST("users.php")
    fun login(
        @Field("num") num: String, @Field("pass") pass: String,
        @Field("condition") condition: String
    ): Call<LoginResponse>



    @FormUrlEncoded
    @POST("users.php")
    fun updateprofile(
        @Field("name") name: String,
        @Field("num") num: String,
        @Field("address") address: String,
        @Field("city") city: String,
        @Field("pass") pass: String,
        @Field("status") status: String,
        @Field("path") path: String,
        @Field("id") id: Int,
        @Field("condition") condition: String,
    ): Call<DefaultResponse>

  @GET("adminuser.php")
  fun adminuser():Call<Userresponse>


    @FormUrlEncoded
    @POST("users.php")
    fun Deleteperson(
        @Field("id") id: Int,
        @Field("condition") condition: String,
    ): Call<DefaultResponse>

}