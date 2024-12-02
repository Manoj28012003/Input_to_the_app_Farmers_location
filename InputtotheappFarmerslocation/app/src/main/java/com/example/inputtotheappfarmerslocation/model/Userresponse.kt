package com.example.inputtotheappfarmerslocation.model




class Userresponse(val error: Boolean, val message:String, var user:ArrayList<User>) {
}


data class User(
    var id:Int,
    var name:String,
    var num:String,
    var email:String,
    var address:String,
    var city:String,
    var pass:String,
    var type:String,
    var status:String,
    var path:String,

)
