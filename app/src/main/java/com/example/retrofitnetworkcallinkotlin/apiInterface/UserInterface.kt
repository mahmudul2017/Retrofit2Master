package com.example.retrofitnetworkcallinkotlin.apiInterface

import com.example.retrofitnetworkcallinkotlin.data.User
import com.example.retrofitnetworkcallinkotlin.data.attendance.AttendanceResponse
import com.example.retrofitnetworkcallinkotlin.data.rgl.RgEmployee
import com.example.retrofitnetworkcallinkotlin.data.rgl.model.EmployeeInfo
import com.example.retrofitnetworkcallinkotlin.data.rglEmployee.RglEmployeeList
import com.example.retrofitnetworkcallinkotlin.postData.PostUserLogin
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserInterface {

    @GET("users")
    fun getUsers(): Call<List<User>>

    @GET("api/employee/attendbyusername")
    fun getRglUserLogin(@Query("param") param: String): Call<RglEmployeeList>

    @POST("api/employee/attendbyusername")
    fun getEmployeeLogin(@Body jsonArray: JsonArray): Call<PostUserLogin>

    @POST("api/employee/setattendancebyusername")
    fun getEmployeeAttendance(@Body jsonArray: JsonArray): Call<AttendanceResponse>
}