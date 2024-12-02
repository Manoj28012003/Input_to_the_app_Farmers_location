package com.example.inputtotheappfarmerslocation.model

import com.google.gson.annotations.SerializedName

class historyresponse(val error: Boolean, val message:String, var user:ArrayList<histor>) {
}

data class histor (

    @SerializedName("mainId"  ) var mainId  : String? = null,
    @SerializedName("uname"   ) var uname   : String? = null,
    @SerializedName("unum"    ) var unum    : String? = null,
    @SerializedName("uemail"  ) var uemail  : String? = null,
    @SerializedName("cemail"  ) var cemail  : String? = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("cdescri" ) var cdescri : String? = null,
    @SerializedName("status"  ) var status  : String? = null,
    @SerializedName("name"    ) var name    : String? = null,
    @SerializedName("num"     ) var num     : String? = null,
    @SerializedName("address" ) var address : String? = null,
    @SerializedName("city"    ) var city    : String? = null,
    @SerializedName("pass"    ) var pass    : String? = null,
    @SerializedName("type"    ) var type    : String? = null,
    @SerializedName("path"    ) var path    : String? = null,
    @SerializedName("descr"   ) var descr   : String? = null,
    @SerializedName("infom"   ) var infom   : String? = null

)