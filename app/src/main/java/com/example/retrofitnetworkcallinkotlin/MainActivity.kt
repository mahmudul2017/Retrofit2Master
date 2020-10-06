package com.example.retrofitnetworkcallinkotlin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitnetworkcallinkotlin.adapter.UserAdapter
import com.example.retrofitnetworkcallinkotlin.apiInterface.UserInterface
import com.example.retrofitnetworkcallinkotlin.data.LoginUser
import com.example.retrofitnetworkcallinkotlin.data.User
import com.example.retrofitnetworkcallinkotlin.data.UserLoginData
import com.example.retrofitnetworkcallinkotlin.data.fake.ApiBody
import com.example.retrofitnetworkcallinkotlin.data.fake.ApiResponse
import com.example.retrofitnetworkcallinkotlin.data.fake.DataMainResponse
import com.example.retrofitnetworkcallinkotlin.data.fake.LastLoginResponse
import com.example.retrofitnetworkcallinkotlin.retrofitClient.RetrofitClient
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.RequestBody
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
        retrofit = RetrofitClient.getInstance(this, "jsonplaceholder")
        userInterface = retrofit!!.create(UserInterface::class.java)

        /*recyclerView = findViewById(R.id.recyclerView)
        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)*/
      //  getAllUsers()

     //   sendLoginRequest()

        sendFakeReques()
    }

    private fun sendFakeReques() {
      val lastLoginObject = LastLoginResponse("dateTime|UNIX", "172.242.228.245");
      val dataObjectClass = ApiResponse("555", "internetEmail", "personGender", "name", lastLoginObject);

        val mainObjectClass = DataMainResponse("MPSfLPolMP80AQJpPc5cYQ",dataObjectClass);
        val call = userInterface.addFakeUserLogin(mainObjectClass)
        call.enqueue(object: Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                tv_postData.text = response.body()?.email
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                tv_postData.text = t.message
            }
        })
    }

    private fun sendLoginRequest() {
        val email = "1probelalkhan@gmail.com"
        val password = 123456
        val userLoginData = UserLoginData(email, password)
        val call = userInterface.addUserLogin(userLoginData)

        call.enqueue(object : Callback<LoginUser> {
            override fun onResponse(call: Call<LoginUser>, response: Response<LoginUser>) {
                if(response.isSuccessful) {
                    tv_postData.text = response.body()?.message
                } else {
                    tv_postData.text = "Invalid email or password"
                }
            }

            override fun onFailure(call: Call<LoginUser>, t: Throwable) {
                tv_postData.text = t.message          }
        })
    }

    private fun getAllUsers() {
        val call: Call<List<User>> = userInterface.getUsers()
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    var userPost: List<User> = response.body()!!
                 //   var post = ""
                 //   val login = userPost.get(0).login
                    userAdapter = UserAdapter( userPost, this@MainActivity)
                    recyclerView.adapter = userAdapter
                    shimmerFrameLayout.stopShimmer()
                    shimmerFrameLayout.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                /*    for (postValue in userPost) {
                    }*/
                  //  tvData.append(login)
                } else {
                  //  tvData.setText("code: " + response.code())
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
              //  tvData.setText("message: " + t.message)
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
