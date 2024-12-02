package com.example.inputtotheappfarmerslocation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.inputtotheappfarmerslocation.Admin.AdminMandishops
import com.example.inputtotheappfarmerslocation.Admin.AdminUsers
import com.example.inputtotheappfarmerslocation.databinding.ActivityAdminDashboardBinding
import com.example.inputtotheappfarmerslocation.model.logout

class AdminDashboard : AppCompatActivity() {
    private  val b by lazy {
        ActivityAdminDashboardBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.bntlogout.setOnClickListener { logout() }
        b.btnadminuser.setOnClickListener { startActivity(Intent(this,AdminUsers::class.java)) }
        b.btnmandishop.setOnClickListener { startActivity(Intent(this,AdminMandishops::class.java)) }
    }
}