package com.example.inputtotheappfarmerslocation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.inputtotheappfarmerslocation.databinding.ActivityMandiDashboardBinding

class mandiDashboard : AppCompatActivity() {
    private  val b by lazy {
        ActivityMandiDashboardBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(b.root)

    }
}