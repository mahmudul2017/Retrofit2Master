package com.example.retrofitnetworkcallinkotlin.data


data class UserLogin(val resdata: PackServiceResdata?)

data class PackServiceResdata(val ispservices: String?)

data class PackService(val employeeId: Int?, val fullName: String?,
                       val designation: Int?, val enrollNumber: Int?,
                       val ssn: String?)