package com.example.retrofitnetworkcallinkotlin.apiInterface

import com.example.retrofitnetworkcallinkotlin.data.User
import com.example.retrofitnetworkcallinkotlin.data.rgl.RgEmployee
import com.example.retrofitnetworkcallinkotlin.data.rglEmployee.RglEmployeeList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserInterface {

    @GET("users")
    fun getUsers(): Call<List<User>>

    @GET("api/employee/attendbyusername")
    fun getRglUserLogin(@Query("param") param: String): Call<RglEmployeeList>
}