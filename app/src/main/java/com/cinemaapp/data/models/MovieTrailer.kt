package com.cinemaapp.data.models

import com.google.gson.annotations.SerializedName


/*
* Created By mabrouk on 20/04/19
* CinemaApp
*/

data class MovieTrailer(@SerializedName("id")val trailer_id: String,@SerializedName("key")val trailer_key:String?,@SerializedName("name") val trailer_name: String){
    val thumbnailImage:String
    get() = "https://img.youtube.com/vi/$trailer_key/0.jpg"
}