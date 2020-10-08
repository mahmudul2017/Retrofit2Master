package com.example.retrofitnetworkcallinkotlin.splash

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.retrofitnetworkcallinkotlin.MainActivity
import com.example.retrofitnetworkcallinkotlin.R
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SplashActivity : AppCompatActivity() {

    lateinit var preferences: SharedPreferences
    private lateinit var animation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

      /*  if (preferences.getBoolean("goToLogin", false)) {
            goToActivity()
         //   findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        }*/

        animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.logo_animation)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
              /*  runBlocking {
                    launch {
                        preferences.edit().apply {
                            putBoolean("goToLogin", true)
                            apply()
                        }
                        delay(1500L)
                    }

                //    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())

                }*/

                goToActivity()
            }

            override fun onAnimationStart(p0: Animation?) {

            }
        })

        logo.startAnimation(animation)
    }

    private fun goToActivity() {
        val goToNext = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(goToNext)
        finish()
    }
}