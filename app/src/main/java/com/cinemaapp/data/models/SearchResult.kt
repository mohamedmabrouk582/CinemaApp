package com.cinemaapp.data.models

import com.cinemaapp.app.MyApp
import com.cinemaapp.data.enums.MediaType
import com.google.gson.annotations.SerializedName


/*
* Created By mabrouk on 06/05/19
* CinemaApp
*/

data class SearchResult(val id:Long, val poster_path:String?, val media_type:MediaType, @SerializedName(value = "original_title",alternate = ["original_name"])val original_title:String, val overview:String?
,@SerializedName(value = "release_date",alternate = ["first_air_date"])val release_date:String?) {
    val media_img:String?
    get() = MyApp.IMG_URL+poster_path
}