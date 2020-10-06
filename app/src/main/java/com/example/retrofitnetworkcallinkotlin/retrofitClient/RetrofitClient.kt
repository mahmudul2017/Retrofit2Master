package com.example.retrofitnetworkcallinkotlin.retrofitClient

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
   // const val baseUrl = ""
    private var retrofit: Retrofit? = null

    @Synchronized
    fun getInstance(context: Context, mBaseUrl: String): Retrofit? {
        if (retrofit == null) {
            createRetrofit(context, mBaseUrl)
        }
        return retrofit
    }

    fun createRetrofit(context: Context, baseUrl: String) {
        val loggingInterceptor = HttpLoggingInterceptor()
        /* Only show basic information in logCat
        * ex... method post, url, http code*/
        //   loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)
        if (baseUrl == "jsonplaceholder") {
            retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
        } else if (baseUrl == "ajkerdeal") {
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.ajkerdeal.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
        } else if (baseUrl == "githubApi") {
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
        }  else if (baseUrl == "simplifiedCoding") {
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.simplifiedcoding.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
        }
    }
}