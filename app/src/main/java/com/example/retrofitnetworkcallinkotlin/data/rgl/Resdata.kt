package com.example.retrofitnetworkcallinkotlin.data.rgl

import com.example.retrofitnetworkcallinkotlin.data.rgl.LoggedInfo

data class Resdata(
    val loggedInfo: LoggedInfo,
    val message: String,
    val resstate: Boolean
)