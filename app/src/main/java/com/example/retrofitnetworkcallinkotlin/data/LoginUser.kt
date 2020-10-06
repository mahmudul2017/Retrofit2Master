package com.example.retrofitnetworkcallinkotlin.data
import com.google.gson.annotations.SerializedName

data class LoginUser(
    @SerializedName("isSuccessful")
    val isSuccessful: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("user")
    val user: UserX?
)