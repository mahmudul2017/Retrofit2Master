package com.example.retrofitnetworkcallinkotlin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitnetworkcallinkotlin.adapter.UserAdapter
import com.example.retrofitnetworkcallinkotlin.apiInterface.UserInterface
import com.example.retrofitnetworkcallinkotlin.data.rgl.LoggedInfo
import com.example.retrofitnetworkcallinkotlin.data.rgl.RgEmployee
import com.example.retrofitnetworkcallinkotlin.data.rgl.model.EmployeeInfo
import com.example.retrofitnetworkcallinkotlin.data.rglEmployee.RglEmployeeList
import com.example.retrofitnetworkcallinkotlin.retrofitClient.RetrofitClient
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class MainActivity : AppCompatActivity() {

    private lateinit var userInterface: UserInterface
    private var retrofit: Retrofit? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

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

        getAllUsers()
    }

    private fun getAllUsers() {
        val userName = "rgl000003"
        val userPass = "12345"
        val employeeInfo = EmployeeInfo(userName, userPass)

       val jsonObject = JsonObject().apply {
            addProperty("userName", employeeInfo.userName)
            addProperty("userPass", employeeInfo.userPass)
        }

        val param = JsonArray().apply {
            add(jsonObject)
        }.toString()

        val call: Call<RglEmployeeList> = userInterface.getRglUserLogin(param)
        call.enqueue(object : Callback<RglEmployeeList> {
            override fun onResponse(call: Call<RglEmployeeList>, response: Response<RglEmployeeList>) {
                Toast.makeText(this@MainActivity, "Login Success...", Toast.LENGTH_LONG).show()
                tv_dataShow.text = response.body()?.resdata?.loggedInfo

                val loggedString= response.body().toString()
                response.body()?.resdata?.loggedInfo?.let {
                    val jsonArray = JsonParser.parseString(it).asJsonArray
                    val packService =
                        Gson().fromJson(jsonArray.toString(), LoggedInfo::class.java)
                    Toast.makeText(this@MainActivity, "" + packService.fullName, Toast.LENGTH_LONG)
                        .show()
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

            override fun onFailure(call: Call<RglEmployeeList>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Login Failed..." + t.message, Toast.LENGTH_LONG).show()
            }

            /* override fun onFailure(call: Call<List<User>>, t: Throwable) {
               //  tvData.setText("message: " + t.message)
             }*/
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
