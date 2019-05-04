package com.cinemaapp.data.models

import com.cinemaapp.app.MyApp
import com.google.gson.annotations.SerializedName


/*
* Created By mabrouk on 03/05/19
* CinemaApp
*/

data class TvNetWorks (@SerializedName("id")val network_id:Long,@SerializedName("name") val network_name:String?
,@SerializedName("logo_path") val network_logo_path:String?,@SerializedName("origin_country") val network_origin_country:String?){
    val network_img:String?
    get() = MyApp.IMG_URL+network_logo_path
}