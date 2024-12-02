package com.example.inputtotheappfarmerslocation.Admin

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inputtotheappfarmerslocation.R
import com.example.inputtotheappfarmerslocation.databinding.ActivityAdminMandishopsBinding
import com.example.inputtotheappfarmerslocation.databinding.CardaddmandishopBinding
import com.example.inputtotheappfarmerslocation.databinding.CarduseradminBinding
import com.example.inputtotheappfarmerslocation.model.RetrofitClient
import com.example.inputtotheappfarmerslocation.model.User
import com.example.inputtotheappfarmerslocation.model.Userresponse
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ymts0579.model.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminMandishops : AppCompatActivity() {
    private val b by lazy {
        ActivityAdminMandishopsBinding.inflate(layoutInflater)
    }
    private val bind by lazy{
        CardaddmandishopBinding.inflate(layoutInflater)
    }

    private lateinit var p: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)


        readmandis()
        b.btnaddmandishop.setOnClickListener {

            BottomSheetDialog(this).apply {
                (bind.root.parent as? ViewGroup)?.removeView(bind.root)
                setContentView(bind.root)


                bind.btnsignup.setOnClickListener{
                    val name=bind.etname.text.toString().trim()
                    val num= bind.etnum.text.toString().trim()
                    val email= bind.etemail1.text.toString().trim()
                    val addres= bind.etaddress.text.toString().trim()
                    val city= bind.etcity.text.toString().trim()
                    val pass= bind.etpassword1.text.toString().trim()

                    if(name.isEmpty()){bind.etname.error="Enter your name"}
                    else if(num.isEmpty()){bind.etnum.error="Enter your number"}
                    else if(email.isEmpty()){bind.etemail1.error="Enter your email "}
                    else if(addres.isEmpty()){bind.etaddress.error="Enter your Address"}
                    else if(city.isEmpty()){bind.etcity.error="Enter your city"}
                    else if(pass.isEmpty()){bind.etpassword1.error="Enter your password "}
                    else {
                        if(num.count()==10){

                            CoroutineScope(Dispatchers.IO).launch {
                                RetrofitClient.instance.register(name,num,email,addres,city,pass,"Mandi","Available","","register")
                                    .enqueue(object: Callback<DefaultResponse> {
                                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                            Toast.makeText(this@AdminMandishops, ""+t.message, Toast.LENGTH_SHORT).show()
                                        }
                                        override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                            Toast.makeText(this@AdminMandishops, "${response.body()!!.message}", Toast.LENGTH_SHORT).show()
                                            bind.etname.text.clear()
                                            bind.etnum.text.clear()
                                            bind.etemail1.text.clear()
                                            bind.etaddress.text.clear()
                                            bind.etcity.text.clear()
                                            bind.etpassword1.text.clear()

                                          dismiss()

                                        }
                                    })
                            }

                        }else{
                            bind.etnum.error="Enter your number properly"
                        }
                    }

                }

                show()
            }

        }

    }

    private fun readmandis() {
        val builder = AlertDialog.Builder(this,R.style.TransparentDialog)
        val inflater = this.layoutInflater
        builder.setView(inflater.inflate(R.layout.progressdialog, null))
        builder.setCancelable(false)
        p = builder.create()
        p.show()

        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.adminuser()
                .enqueue(object : Callback<Userresponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<Userresponse>, response: Response<Userresponse>) {

                        b.listmandi.let {
                            response.body()?.user?.let {
                                    it1 ->
                                it.adapter=   mandiAdminAdapter(this@AdminMandishops, it1)
                                it.layoutManager= LinearLayoutManager(this@AdminMandishops)
                                Toast.makeText(this@AdminMandishops, "success", Toast.LENGTH_SHORT).show()
                            }
                        }
                        p.dismiss()

                    }

                    override fun onFailure(call: Call<Userresponse>, t: Throwable) {
                        Toast.makeText(this@AdminMandishops, "${t.message}", Toast.LENGTH_SHORT).show()
                        p.dismiss()
                    }

                })
        }
    }

    class mandiAdminAdapter(var context: Context, var listdata: ArrayList<User>):
        RecyclerView.Adapter<mandiAdminAdapter.DataViewHolder>(){

        inner class DataViewHolder(val view: CarduseradminBinding) : RecyclerView.ViewHolder(view.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
            return DataViewHolder(
                CarduseradminBinding.inflate(
                    LayoutInflater.from(context),parent,
                    false))
        }

        override fun onBindViewHolder(holder: DataViewHolder, @SuppressLint("RecyclerView") position:Int) {
            with(holder.view){

                listdata[position].apply {
                    tvfname.text=name
                    tvfemail.text=email
                    tvfnum.text=num
                    tvfcity.text=city
                    tvstatus.text=status




                    btndelete.setOnClickListener {
                        val alertdialog= AlertDialog.Builder(context)
                        alertdialog.setTitle("Delete")
                        alertdialog.setIcon(R.drawable.logo)
                        alertdialog.setCancelable(false)
                        alertdialog.setMessage("Do you Deleted the Profile?")
                        alertdialog.setPositiveButton("Yes"){ alertdialog, which->

                            deleteperson(id)
                            alertdialog.dismiss()
                        }

                        alertdialog.show()
                    }

                }

            }




        }

        private fun deleteperson(id: Int) {
            CoroutineScope(Dispatchers.IO).launch {
                RetrofitClient.instance.Deleteperson(id,"deletetable")
                    .enqueue(object: Callback<DefaultResponse> {
                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                            Toast.makeText(context, ""+t.message, Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                            Toast.makeText(context, "${response.body()!!.message }", Toast.LENGTH_SHORT).show()


                        }
                    })
            }

        }


        override fun getItemCount() = listdata.size
    }
}