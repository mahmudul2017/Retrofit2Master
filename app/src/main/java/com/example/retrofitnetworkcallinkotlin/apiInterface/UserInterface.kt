package com.example.retrofitnetworkcallinkotlin.apiInterface

import com.example.retrofitnetworkcallinkotlin.data.LoginUser
import com.example.retrofitnetworkcallinkotlin.data.User
import com.example.retrofitnetworkcallinkotlin.data.UserLoginData
import com.example.retrofitnetworkcallinkotlin.data.fake.ApiResponse
import com.example.retrofitnetworkcallinkotlin.data.fake.DataMainResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserInterface {

    @GET("users")
    fun getUsers(): Call<List<User>>

    @POST("course-apis/mvvm/login")
    fun addUserLogin(@Body userLoginData: UserLoginData): Call<LoginUser>

    @POST("q")
    fun addFakeUserLogin(@Body dataMainResponse: DataMainResponse): Call<ApiResponse>
}