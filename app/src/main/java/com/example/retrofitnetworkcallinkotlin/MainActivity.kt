package com.example.retrofitnetworkcallinkotlin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitnetworkcallinkotlin.adapter.UserAdapter
import com.example.retrofitnetworkcallinkotlin.apiInterface.UserInterface
import com.example.retrofitnetworkcallinkotlin.data.User
import com.example.retrofitnetworkcallinkotlin.retrofitClient.RetrofitClient
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.activity_main.*
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
        retrofit = RetrofitClient.getInstance(this, "githubApi")
        userInterface = retrofit!!.create(UserInterface::class.java)

        recyclerView = findViewById(R.id.recyclerView)
        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        getAllUsers()
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
