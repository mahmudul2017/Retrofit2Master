package com.example.retrofitnetworkcallinkotlin.data.fake

data class ApiResponse(
    var name: String? = null,
    var email: String? = null,
    var id: String? = null,
    var gender: String? = null,
    var last_login: LastLoginResponse? = null
)