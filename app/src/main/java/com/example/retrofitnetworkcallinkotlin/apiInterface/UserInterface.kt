package com.example.retrofitnetworkcallinkotlin.apiInterface

import com.example.retrofitnetworkcallinkotlin.data.User
import retrofit2.Call
import retrofit2.http.GET

interface UserInterface {

    @GET("users")
    fun getUsers(): Call<List<User>>
}