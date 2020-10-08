package com.example.retrofitnetworkcallinkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.retrofitnetworkcallinkotlin.apiInterface.UserInterface
import com.example.retrofitnetworkcallinkotlin.data.attendance.AttendanceResponse
import com.example.retrofitnetworkcallinkotlin.data.test.Test
import com.example.retrofitnetworkcallinkotlin.postData.PostEmployeeData
import com.example.retrofitnetworkcallinkotlin.retrofitClient.RetrofitClient
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_attendance.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class UserAttendanceActivity : AppCompatActivity() {
    private lateinit var userInterface: UserInterface
    private var retrofit: Retrofit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)

        retrofit = RetrofitClient.getInstance(this, "royalGreen")
        userInterface = retrofit!!.create(UserInterface::class.java)
      /*  intent.let {
            val loggedInfo = intent.extras?.getParcelable<PostEmployeeData>(MainActivity.LOGGED_USER) as PostEmployeeData
            tv_userData.text = loggedInfo.toString()
        }*/

        getEmployeeAttendance()
    }

    private fun getEmployeeAttendance() {
        val loggedInfo = intent.getParcelableExtra<PostEmployeeData>("Logged_User")
    //    tv_userData.text = loggedInfo.toString()
        Toast.makeText(this@UserAttendanceActivity, "User Post Data: " + loggedInfo, Toast.LENGTH_LONG).show()

        val latitude = "120345"
        val langtitude = "120345"

        val jsonObject = JsonObject().apply {
            addProperty("employeeId", loggedInfo.employeeId)
            addProperty("fullName", loggedInfo.fullName)
            addProperty("designation", loggedInfo.designation)
            addProperty("enrollNumber", loggedInfo.enrollNumber)
            addProperty("ssn", loggedInfo.ssn)
            addProperty("latitude", latitude)
            addProperty("longitude", langtitude)
        }

        val param = JsonArray().apply {
            add(jsonObject)
        }

        val call: Call<AttendanceResponse> = userInterface.getEmployeeAttendance(param)
        call.enqueue(object : Callback<AttendanceResponse> {
            override fun onResponse(call: Call<AttendanceResponse>, response: Response<AttendanceResponse>) {
                Toast.makeText(this@UserAttendanceActivity, "User Attendance..." + response.body()?.resdata, Toast.LENGTH_LONG).show()
                tv_userData.text = response.body()?.resdata.toString()
            }

            override fun onFailure(call: Call<AttendanceResponse>, t: Throwable) {
                Toast.makeText(this@UserAttendanceActivity, "User Attendance Failed..." + t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}