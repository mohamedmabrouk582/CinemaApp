package com.cinemaapp.data.models

import com.cinemaapp.app.MyApp


/*
* Created By mabrouk on 15/04/19
* CinemaApp
*/

data class MovieImage(val aspect_ratio:Float,val file_path:String?,val height:Int,val width:Int){
        val poster_path1:String
        get() = MyApp.IMG_URL+file_path
}