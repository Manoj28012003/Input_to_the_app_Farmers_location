package com.example.inputtotheappfarmerslocation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.inputtotheappfarmerslocation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val b by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)


        val type=getSharedPreferences("user", MODE_PRIVATE).getString("type", "")!!

        when(type){
            "admin"-> {
                startActivity(Intent(this, AdminDashboard::class.java))
                finish()
            }
            "User"->{
                startActivity(Intent(this, UserDashboard::class.java))
                finish()
            }
            "Mandi"->{
                startActivity(Intent(this, mandiDashboard::class.java))
                finish()
            }
            else->{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    Handler.createAsync(Looper.myLooper()!!).postDelayed({
                        startActivity(Intent(this,Login::class.java))
                        finish()
                    },3000)
                }else{
                    Handler().postDelayed({
                        startActivity(Intent(this,Login::class.java))
                        finish()
                    },3000)
                }
            }
        }

    }
}