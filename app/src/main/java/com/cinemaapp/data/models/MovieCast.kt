package com.cinemaapp.data.models

import com.cinemaapp.app.MyApp
import com.google.gson.annotations.SerializedName


/*
* Created By mabrouk on 20/04/19
* CinemaApp
*/

data class MovieCast(val cast_id:Long,val character:String,val credit_id:String,val gender:Int,@SerializedName("name")val cast_name:String,
                     @SerializedName("profile_path") val cast_image:String?) {
    val cast_image_path:String
    get() = MyApp.IMG_URL+cast_image
}