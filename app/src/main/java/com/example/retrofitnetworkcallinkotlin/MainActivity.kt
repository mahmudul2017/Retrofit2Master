package com.example.retrofitnetworkcallinkotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitnetworkcallinkotlin.adapter.UserAdapter
import com.example.retrofitnetworkcallinkotlin.apiInterface.UserInterface
import com.example.retrofitnetworkcallinkotlin.data.attendance.AttendanceResponse
import com.example.retrofitnetworkcallinkotlin.data.rgl.model.EmployeeInfo
import com.example.retrofitnetworkcallinkotlin.data.test.Test
import com.example.retrofitnetworkcallinkotlin.postData.PostEmployeeData
import com.example.retrofitnetworkcallinkotlin.postData.PostUserLogin
import com.example.retrofitnetworkcallinkotlin.retrofitClient.RetrofitClient
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var userInterface: UserInterface
    private var retrofit: Retrofit? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    companion object {
        const val LOGGED_USER = "Logged_User"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shimmerFrameLayout = findViewById(R.id.shimmerLayout)
        retrofit = RetrofitClient.getInstance(this, "royalGreen")
        userInterface = retrofit!!.create(UserInterface::class.java)

        /*  recyclerView = findViewById(R.id.recyclerView)
          var layoutManager = LinearLayoutManager(this)
          recyclerView.layoutManager = layoutManager
          recyclerView.setHasFixedSize(true)*/

        getEmployeeLogin()
      //  getEmployeeAttendance()

    /*    btn_next.setOnClickListener {
            val userAttendance = Intent(this, UserAttendanceActivity::class.java)
            userAttendance.putExtra(LOGGED_USER, loggedInfo)
            startActivity(userAttendance)
        }*/
    }

    private fun getEmployeeLogin() {
        val userName = "rgl000003"
        val userPass = "12345"
        val employeeInfo = EmployeeInfo(userName, userPass)

        val jsonObject = JsonObject().apply {
            addProperty("userName", employeeInfo.userName)
            addProperty("userPass", employeeInfo.userPass)
        }

        val param = JsonArray().apply {
            add(jsonObject)
        }

      /*  val param = JsonArray().apply {
            add(jsonObject)
        }.toString()*/

        val call: Call<PostUserLogin> = userInterface.getEmployeeLogin(param)
        call.enqueue(object : Callback<PostUserLogin> {
            override fun onResponse(call: Call<PostUserLogin>, response: Response<PostUserLogin>) {
                Toast.makeText(this@MainActivity, "Login Success...", Toast.LENGTH_LONG).show()
                tv_dataShow.text = response.body().toString()

                try {
                    response.body()?.resdata?.loggedInfo?.let {
                        val jsonArray = JsonParser.parseString(it).asJsonArray[0].asJsonObject
                            val packService =
                                Gson().fromJson(jsonArray, PostEmployeeData::class.java)
                            Toast.makeText(
                                this@MainActivity,
                                "" + packService.enrollNumber,
                                Toast.LENGTH_LONG
                            ).show()
                        
                        val userData = PostEmployeeData(packService.designation, packService.employeeId, packService.enrollNumber,
                        packService.fullName, packService.ssn)

                        Toast.makeText(
                            this@MainActivity,
                            "" + userData,
                            Toast.LENGTH_LONG
                        ).show()

                   /*    val firstName = "Md. "
                        val lastName = "Hasan"

                        val test = Test(firstName, lastName)*/

                        btn_next.setOnClickListener {
                            val userAttendance = Intent(this@MainActivity, UserAttendanceActivity::class.java)
                            userAttendance.putExtra("Logged_User", userData)
                            startActivity(userAttendance)
                        }
                    }

//                    val loggedUser = response.body()?.resdata?.loggedInfo
//                    val fromUser = Gson().fromJson(loggedUser, LoggedInfo::class.java)
//                    Toast.makeText(this@MainActivity, "fullName :" + fromUser.fullName, Toast.LENGTH_LONG).show()

                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "" + e.message, Toast.LENGTH_LONG).show()
                }
                /*  val packService =
                      Gson().fromJson(loggedString, LoggedInfo::class.java)
                  Toast.makeText(this@MainActivity, "" + packService.fullName, Toast.LENGTH_LONG)
                      .show()*/
                /*    if (response.isSuccessful) {
                        var userPost: List<User> = response.body()!!
                     //   var post = ""
                     //   val login = userPost.get(0).login
                        userAdapter = UserAdapter( userPost, this@MainActivity)
                        recyclerView.adapter = userAdapter
                        shimmerFrameLayout.stopShimmer()
                        shimmerFrameLayout.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    *//*    for (postValue in userPost) {
                    }*//*
                  //  tvData.append(login)
                } else {
                  //  tvData.setText("code: " + response.code())
                }*/
            }

            override fun onFailure(call: Call<PostUserLogin>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Login Failed..." + t.message, Toast.LENGTH_LONG)
                    .show()
            }

            /* override fun onFailure(call: Call<List<User>>, t: Throwable) {
               //  tvData.setText("message: " + t.message)
             }*/
        })
    }

    private fun getEmployeeAttendance() {
        val postEmployeeData = PostEmployeeData("", 0, "", "", "")
        val latitude = "120345"
        val langtitude = "120345"

        val jsonObject = JsonObject().apply {
            addProperty("employeeId", postEmployeeData.employeeId)
            addProperty("fullName", postEmployeeData.fullName)
            addProperty("designation", postEmployeeData.designation)
            addProperty("enrollNumber", postEmployeeData.enrollNumber)
            addProperty("ssn", postEmployeeData.ssn)
            addProperty("latitude", latitude)
            addProperty("longitude", langtitude)
        }

        val param = JsonArray().apply {
            add(jsonObject)
        }

        val call: Call<AttendanceResponse> = userInterface.getEmployeeAttendance(param)
        call.enqueue(object : Callback<AttendanceResponse> {
            override fun onResponse(call: Call<AttendanceResponse>, response: Response<AttendanceResponse>) {
                Toast.makeText(this@MainActivity, "User Attendance...", Toast.LENGTH_LONG).show()
                tv_dataShow.text = response.body()?.resdata.toString()
            }

            override fun onFailure(call: Call<AttendanceResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Login Failed..." + t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmer()
    }

    override fun onStop() {
        super.onStop()
        shimmerFrameLayout.stopShimmer()
    }
}
