package com.example.inputtotheappfarmerslocation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.inputtotheappfarmerslocation.databinding.ActivityUserDashboardBinding
import com.example.inputtotheappfarmerslocation.model.logout

class UserDashboard : AppCompatActivity() {
    private val b by lazy {
        ActivityUserDashboardBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.btnuserprofile.setOnClickListener {
            startActivity(Intent(this,profile::class.java))
        }
        b.btnuserlogout.setOnClickListener { logout() }

    }
}