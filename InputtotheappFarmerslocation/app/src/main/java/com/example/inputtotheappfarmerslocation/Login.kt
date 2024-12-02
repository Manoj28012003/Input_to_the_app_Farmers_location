package com.example.inputtotheappfarmerslocation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inputtotheappfarmerslocation.model.RetrofitClient
import com.example.inputtotheappfarmerslocation.databinding.ActivityLoginBinding
import com.ymts0579.model.model.DefaultResponse
import com.ymts0579.model.model.LoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    private val b by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.linearregister.setOnClickListener {
            b.linearelogin.visibility= View.GONE
            b.linearbtnregister.visibility=View.VISIBLE
        }

        //login
        b.btnsignin.setOnClickListener {
          val number= b.etemail.text.toString().trim()
          val password=b.etpassword.text.toString().trim()

            if(number.isEmpty()){
                b.etemail.error="Enter your Number"
            }else if(password.isEmpty()){
                b.etpassword.error="Enter your password"

            }else if(number.contains("12345")&& password.contains("admin")){
                getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).edit().putString("type","admin").apply()
                startActivity(Intent(this,AdminDashboard::class.java))
                finish()
            }else if(number.count()!=10){
                b.etemail.error="Enter your Number properly"
            }else{
                CoroutineScope(Dispatchers.IO).launch {
                    RetrofitClient.instance.login(number,password,"login")
                        .enqueue(object: Callback<LoginResponse> {
                            override fun onResponse(
                                call: Call<LoginResponse>, response: Response<LoginResponse>
                            ) {
                                if(!response.body()?.error!!){
                                    val type=response.body()?.user
                                    if (type!=null) {
                                        getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).edit().apply {
                                            putString("num",type.num)
                                            putString("pass",type.pass)
                                            putString("email",type.email)
                                            putString("name",type.name)
                                            putString("address",type.address)
                                            putString("type",type.type)
                                            putString("status",type.status)
                                            putString("path",type.path)

                                            putString("city",type.city)
                                            putInt("id",type.id)
                                            apply()
                                        }

                                        when(type.type){
                                            "User"->{
                                                startActivity(Intent(this@Login, UserDashboard::class.java))
                                                finish()
                                            }
                                            "Mandi"->{
                                                startActivity(Intent(this@Login, mandiDashboard::class.java))
                                                finish()
                                            }
                                        }

                                        Toast.makeText(this@Login, response.body()?.message, Toast.LENGTH_SHORT).show()
                                    }
                                }else{
                                    Toast.makeText(this@Login, response.body()?.message, Toast.LENGTH_SHORT).show()
                                }

                            }

                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                Toast.makeText(this@Login, t.message, Toast.LENGTH_LONG).show()


                            }

                        })
                }
            }
        }



        //Register
        b.btnsignup.setOnClickListener{
            val name=b.etname.text.toString().trim()
            val num= b.etnum.text.toString().trim()
            val email= b.etemail1.text.toString().trim()
            val addres= b.etaddress.text.toString().trim()
            val city= b.etcity.text.toString().trim()
            val pass= b.etpassword1.text.toString().trim()

            if(name.isEmpty()){b.etname.error="Enter your name"}
            else if(num.isEmpty()){b.etnum.error="Enter your number"}
            else if(email.isEmpty()){b.etemail1.error="Enter your email "}
            else if(addres.isEmpty()){b.etaddress.error="Enter your Address"}
            else if(city.isEmpty()){b.etcity.error="Enter your city"}
            else if(pass.isEmpty()){b.etpassword1.error="Enter your password "}
            else {
                if(num.count()==10){
                    Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show()
                    CoroutineScope(Dispatchers.IO).launch {
                        RetrofitClient.instance.register(name,num,email,addres,city,pass,"User","","","register")
                            .enqueue(object: Callback<DefaultResponse> {
                                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                    Toast.makeText(this@Login, ""+t.message, Toast.LENGTH_SHORT).show()
                                }
                                override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                    Toast.makeText(this@Login, "${response.body()!!.message}", Toast.LENGTH_SHORT).show()
                                    b.etname.text.clear()
                                    b.etnum.text.clear()
                                    b.etemail.text.clear()
                                    b.etaddress.text.clear()
                                    b.etcity.text.clear()
                                    b.etpassword1.text.clear()
                                    b.linearelogin.visibility= View.VISIBLE
                                    b.linearbtnregister.visibility=View.GONE
                                    b.etemail.setText(num)
                                    b.etpassword.setText(pass)

                                }
                            })
                    }

                }else{
                    b.etnum.error="Enter your number properly"
                }
            }

        }

    }
}