package com.example.inputtotheappfarmerslocation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.inputtotheappfarmerslocation.databinding.ActivityProfileBinding
import com.example.inputtotheappfarmerslocation.model.RetrofitClient
import com.ymts0579.model.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class profile : AppCompatActivity() {
    private val b by lazy{
        ActivityProfileBinding.inflate(layoutInflater)
    }
    var name=""
    var num=""
    var email=""
    var address=""
    var city=""
    var pass=""
    var type=""
    var status=""
    var path=""
    var id=0
    private lateinit var p: AlertDialog
    var uri: Uri? = null
    var encoded=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        val builder = AlertDialog.Builder(this,R.style.TransparentDialog)
        val inflater = this.layoutInflater
        builder.setView(inflater.inflate(R.layout.progressdialog, null))
        builder.setCancelable(false)
        p = builder.create()
        p.show()

        val activity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data?.data?.let {
                b.imagadd.setImageURI(it)
                uri = it
            }
        }

        b.imagadd.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).apply {
                type="image/*"
                activity.launch(this)
            }
        }

        getSharedPreferences("user", MODE_PRIVATE).apply {
            id=getInt("id",0).toInt()
            name=getString("name","").toString()
            num=getString("num","").toString()
            email=getString("email","").toString()
            address=getString("address","").toString()
            city=getString("city","").toString()
            pass=getString("pass","").toString()
            type=getString("type","").toString()
            status=getString("status","").toString()
            path=getString("path","").toString()


        }

        b.etname.setText(name)
        b.etnum.setText(num)
        b.etemail.setText(email)
        b.etaddress.setText(address)
        b.etcity.setText(city)
        b.etpassword1.setText(pass)

        if(path.isNotEmpty()){
            Glide.with(this).load(Uri.parse(path.trim())).into(b.imagadd)
            p.dismiss()
        }

        val k=arrayOf("choose your choice","Available","Not Available")

        ArrayAdapter(this@profile,
            android.R.layout.simple_list_item_checked, k).apply {
            b.spinstatus.adapter=this
        }
        k.forEachIndexed { index, s ->
            if(s==status){
                b.spinstatus.setSelection(index,true)
            }
        }


        b.btnsubmit.setOnClickListener {
            val name1=b.etname.text.toString().trim()
            val num1= b.etnum.text.toString().trim()
            val email1= b.etemail.text.toString().trim()
            val addres1= b.etaddress.text.toString().trim()
            val city1= b.etcity.text.toString().trim()
            val pass1= b.etpassword1.text.toString().trim()
            val status1=b.spinstatus.selectedItem.toString()

            if(name1.isEmpty()){b.etname.error="Enter Company name"}
            else if(num1.isEmpty()){b.etnum.error="Enter Company number"}
            else if(email1.isEmpty()){b.etemail.error="Enter Company email "}
            else if(addres1.isEmpty()){b.etaddress.error="Enter Company Address"}
            else if(city1.isEmpty()){b.etcity.error="Enter Company city"}
            else if(pass1.isEmpty()){b.etpassword1.error="Enter Company password "}

            else if(status1=="choose your choice"){
                Toast.makeText(this, "choose your choice", Toast.LENGTH_SHORT).show()
            }else if(uri==null){
                Toast.makeText(this, "choose your choice", Toast.LENGTH_SHORT).show()
            } else {
                if(num.count()==10){
                    contentResolver.openInputStream(uri!!)?.readBytes()?.let {
                        encoded= Base64.encodeToString(it, Base64.NO_WRAP)
                        CoroutineScope(Dispatchers.IO).launch {
                            RetrofitClient.instance.updateprofile(
                                name1,num1,addres1,city1,pass1,status1,encoded,id,"updateprofile")
                                .enqueue(object: Callback<DefaultResponse> {
                                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                        Toast.makeText(this@profile, ""+t.message, Toast.LENGTH_SHORT).show()
                                    }
                                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                        Toast.makeText(this@profile,
                                            response.body()!!.message+"a$addres1", Toast.LENGTH_SHORT).show()

                                        getSharedPreferences("user", MODE_PRIVATE).edit().apply {
                                            putString("num",num1)
                                            putString("pass",pass1)
                                            putString("email",email1)
                                            putString("name",name1)
                                            putString("address",addres1)
                                            putString("type",type)
                                            putString("status",status1)
                                            putString("path",encoded)
                                            putString("city",city1)
                                            putInt("id",id)
                                            apply()

                                        }

                                    }
                                })
                        }
                    }



                }else{
                    b.etnum.error="Enter your number properly"
                }
            }

        }

    }
}