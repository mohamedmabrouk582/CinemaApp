package com.cinemaapp.data.models

import com.cinemaapp.app.MyApp
import com.google.gson.annotations.SerializedName


/*
* Created By mabrouk on 03/05/19
* CinemaApp
*/

data class TvShowCreated (@SerializedName("id")val created_id:Long , @SerializedName("name")val created_name: String,@SerializedName("credit_id")val created_credit_id: String?
,@SerializedName("profile_path") val created_profile_path: String?,val gender:Int){
    val created_img:String?
    get() = MyApp.IMG_URL+created_profile_path
}